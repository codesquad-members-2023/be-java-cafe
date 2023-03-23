package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "INSERT INTO CAFEUSER(USER_ID, PASSWORD, NAME, EMAIL) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public List<UserDto> findUserAll() {
        String sql = "SELECT * FROM CAFEUSER";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class));
    }

    public Optional<UserDto> findUserByUserId(String userId) {
        String sql = "SELECT * FROM CAFEUSER WHERE USER_ID = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), userId));
    }

    public void updateUser(UserDto userDto) {
        String sql = "UPDATE CAFEUSER SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USER_ID = ? AND PASSWORD = ?";
        jdbcTemplate.update(sql,
                userDto.getPassword(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getUserId(),
                userDto.getPassword()
        );
    }

    public Optional<UserDto> findUserByUserPassword(String password) {
        String sql = "SELECT * FROM CAFEUSER WHERE PASSWORD = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
