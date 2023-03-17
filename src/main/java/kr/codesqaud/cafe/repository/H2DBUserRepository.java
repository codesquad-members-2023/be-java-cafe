package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
    public User findByUserId(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(int id, User updateUser, String newPassword) {

    }
}
