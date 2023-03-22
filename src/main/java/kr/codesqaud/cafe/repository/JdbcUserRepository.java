package kr.codesqaud.cafe.repository;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void save(User user) {
        //Todo: `BeanPropertySqlParameterSource` 사용해서 파라미터 값 받을 수 있도록 수정
        String sql = "INSERT INTO members (id, name, password, email) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE name=?, password=?, email=?";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getName(), user.getPassword(), user.getEmail());
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from members", userRowMapper());
    }

    @Override
    public Optional<User> findById(String id) {
        List<User> result = jdbcTemplate.query("select * from members where id = ?", userRowMapper(), id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return result.stream().findAny();
    }

    private RowMapper<User> userRowMapper() { //sql 결과를 받기위해 row mapping 필요
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            }
        };
    }
}
