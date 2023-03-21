package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean save(User user) {
        // 아이디 중복 여부 확인
        if (findByUserId(user.getUserId()).isEmpty()) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("CAFE_USER").usingGeneratedKeyColumns("ID");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("USERID", user.getUserId());
            parameters.put("PASSWORD", user.getPassword());
            parameters.put("NAME", user.getName());
            parameters.put("EMAIL", user.getEmail());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            user.setId(key.longValue());
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        // 해당 번호 존재여부 체크
        if (!findById(user.getId()).isEmpty()) {
            jdbcTemplate.update("update cafe_user set USERID=?, PASSWORD=?, NAME=?, EMAIL=? where ID=?",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getId());
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        List<User> result = jdbcTemplate.query("select * from cafe_user where USERID = ?", userRowMapper(), userId);
        return result.stream().findAny();
    }

    public Optional<User> findById(long id) {
        List<User> result = jdbcTemplate.query("select * from cafe_user where ID = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from cafe_user", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("ID"));
            user.setUserId(rs.getString("USERID"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setName(rs.getString("NAME"));
            user.setEmail(rs.getString("EMAIL"));
            return user;
        };
    }
}
