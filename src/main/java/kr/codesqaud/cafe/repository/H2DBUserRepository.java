package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Primary
public class H2DBUserRepository implements UserRepository{

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public H2DBUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "insert into users (user_id, password, name, email) " +
                "values (:userId, :password, :name, :email)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(user);

        template.update(sql, param);
    }

    @Override
    public User findById(int id) {
        String sql = "select id, user_id, password, name, email from users where id=:id";

        try {
            Map<String, Integer> param = Map.of("id", id);
            User user = template.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(User.class));
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다.");
        }
    }

    @Override
    public User findByUserId(String userId) {
        String sql = "select id, user_id, password, name, email from users where user_id=:user_id";

        try {
            Map<String, String> param = Map.of("user_id", userId);
            User user = template.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(User.class));
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다.");
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, user_id, password, name, email from users";

        return template.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public void update(int id, User updateUser, String oldPassword) {
        User existUser = findById(id);

        if (!existUser.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("[ERROR] 비밀번호가 틀립니다.");
        }

        existUser.setName(updateUser.getName());
        existUser.setPassword(updateUser.getPassword());
        existUser.setEmail(updateUser.getEmail());

        String sql = "update users " +
                "set user_id=:userId, password=:password, name=:name, email=:email " +
                "where id=:id";

        SqlParameterSource param = new BeanPropertySqlParameterSource(existUser);

        template.update(sql, param);
    }
}
