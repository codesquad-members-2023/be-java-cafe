package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import kr.codesqaud.cafe.model.User;

public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        //System.out.println();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(jdbcTemplate.query("select * from users where id = ?", userRowMapper(), id).get(0));
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) ->
                new User(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("index"));
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("insert into USERS(id,password,name,email) values(?, ?, ?, ?)", user.getId(),
                user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public void updateUser(String userId, String password, String newPassword, String name, String email) throws
            IllegalArgumentException {
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("[ERROR] Wrong password"));
        user.validate(password);

        jdbcTemplate.update("update users set password = ?, name = ?, email = ? where id = ?",newPassword, name, email, userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }
}
