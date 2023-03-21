package kr.codesqaud.cafe.repository.member;

import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class H2JDBCMemberRepository implements MemberRepository {

    private final JdbcTemplate template;

    @Autowired
    public H2JDBCMemberRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        String sql = "insert into member (userid, password, name, email) values(?, ?, ?, ?)";

        template.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    @Override
    public User findById(String userId) {
        String sql = "select * from member where userid = ?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from member";

        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void updateUser(User updateUser) {
        String sql = "update member set name=?, email=? where userid=?";

            template.update(sql, updateUser.getName(), updateUser.getEmail(), updateUser.getUserId());
    }
}
