package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kr.codesqaud.cafe.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO USER(USER_ID, PASSWORD, NAME, EMAIL) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public List<UserDto> findUserAll() {
        String sql = "SELECT * FROM USER";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class));
    }

    public List<UserDto> findUserByUserId(String userId) {
        String sql = "SELECT * FROM USER WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), userId);
    }

    public void updateUser(User user) {
        String sql = "UPDATE USER SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USER_ID = ? AND PASSWORD = ?";
        jdbcTemplate.update(sql,
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUserId(),
                user.getPassword()
        );
    }
}
