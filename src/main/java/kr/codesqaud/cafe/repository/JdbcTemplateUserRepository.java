package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean save(User user) {
        // 아이디 중복 여부 확인
        if (findById(user.getUserId()).isEmpty()) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("cafe_user").usingGeneratedKeyColumns("userNum");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("userId", user.getUserId());
            parameters.put("password", user.getPassword());
            parameters.put("name", user.getName());
            parameters.put("email", user.getEmail());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            user.setUserNum(key.longValue());
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        // 해당 번호 존재여부 체크
        if(!findByUserNum(user.getUserNum()).isEmpty()) {
            jdbcTemplate.update("update cafe_user set userId=?, password=?, name=?, email=? where userNum=?",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserNum());
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findById(String userId) {
        List<User> result = jdbcTemplate.query("select * from cafe_user where userId = ?", userRowMapper(), userId);
        return result.stream().findAny();
    }

    public Optional<User> findByUserNum(long userNum) {
        List<User> result = jdbcTemplate.query("select * from cafe_user where userNum = ?", userRowMapper(), userNum);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from cafe_user", userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserNum(rs.getLong("userNum"));
            user.setUserId(rs.getString("userId"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }
}
