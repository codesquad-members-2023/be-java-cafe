package kr.codesqaud.cafe.repository.users;

import kr.codesqaud.cafe.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findDBUser(String userId, String password) {
        String existUserQuery = "select id, userId, password from member where userId = ?";

        log.info("Repository userId password [{}][{}]", userId, password);

        User loginUser = jdbcTemplate.queryForObject(existUserQuery, new BeanPropertyRowMapper<>(User.class), userId);

        return loginUser;
    }

    @Override
    public void update(User user, long id) {
        String sql = "update member set password = ?, name = ?, email = ? where id = ?";

        log.info("update");

        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), id);
    }

    @Override
    public void save(User user) {
        String sql = "insert into member (userId, password, name, email) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "select id, userId, password, name, email, createdAt from member";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User findUserById(long id) {
        String sql = "select id, userId, password, name, email, createdAt from member where id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }
}
