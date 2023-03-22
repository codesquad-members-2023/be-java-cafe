package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import kr.codesqaud.cafe.utils.UserInfoException;
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
            throw new UserInfoException("존재하지 않는 ID입니다. 아이디를 확인해주세요.");
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
        user.validate(password);

        jdbcTemplate.update("update users set password = ?, name = ?, email = ? where id = ?",
            newPassword, name, email, userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select id, password, name, email, idx from users",
            userRowMapper());
    }
}
