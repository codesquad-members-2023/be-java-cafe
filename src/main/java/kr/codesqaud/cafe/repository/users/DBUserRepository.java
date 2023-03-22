package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Primary
@Repository
public class DBUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "insert into member (userId, password, name, email) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getName(), user.getEmail());

    }

    @Override
    public List<User> findAllUsers() {
        String sql = "select * from member";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User findUserById(long id) {
        String sql = "select * from member where id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }
}
