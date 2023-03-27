package kr.codesqaud.cafe.repository;

import java.util.List;

import javax.sql.DataSource;

import kr.codesqaud.cafe.exceptions.UserInfoException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.model.User;

public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(String id) throws UserInfoException {
        try {
            return jdbcTemplate.queryForObject("select * from users where id = ?",
                userRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserInfoException(UserInfoException.INVALID_ID_MESSAGE, UserInfoException.INVALID_ID_CODE);
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) ->
            new User(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"), rs.getInt("idx"));
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("insert into users(id,password,name,email) values(?, ?, ?, ?)",
            user.getId(),
            user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public void updateUser(String userId, String password, String newPassword, String name,
        String email) throws UserInfoException {
        User user = findById(userId);

        if (!user.validate(password)) {
            throw new UserInfoException(UserInfoException.WRONG_PASSWORD_MESSAGE,
                    UserInfoException.WRONG_MODIFICATION_PASSWORD_CODE);
        }

        jdbcTemplate.update("update users set password = ?, name = ?, email = ? where id = ?",
            newPassword, name, email, userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select id, password, name, email, idx from users",
            userRowMapper());
    }
}
