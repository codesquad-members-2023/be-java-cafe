package kr.codesqaud.cafe.repository.h2Repository;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class H2UserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger("user database");

    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    };

    @Override
    public void join(User user) {
        String sql = "insert into users(userId, password, name, email) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public Optional<User> findUserById(String userId) {
        String sql = "select userId, password, name, email from users where userId = ?";

        try  {
            return Optional.of(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select userId, password, name, email  from users";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int update(User user) {
        String sql = "update users set password=? , name=?, email=? where userId=?";

        return jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
}
