package kr.codesqaud.cafe.cafeservice.repository.member;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class H2JdbcTemplateMemberRepository implements MemberRepository {
    private JdbcTemplate template;

    public H2JdbcTemplateMemberRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {
        if (vaildMemberId(member.getUserName())) {
            String sql = "INSERT INTO MEMBER (user_name, EMAIL, PASSWORD) VALUES (?, ?, ?)";
            template.update(sql,
                    member.getUserName(),
                    member.getEmail(),
                    member.getPassword());
        }
    }

    private boolean vaildMemberId(String userName) {
        String sql = "SELECT ID, user_name FROM MEMBER WHERE id = ?";
        return template.query(sql, new MemberRowMapper(), userName).isEmpty();
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, user_name, password ,email,created_date FROM MEMBER";
        return template.query(sql, new MemberRowMapper());
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT  id, user_name, password,email,created_date FROM MEMBER WHERE ID = ?";
        try {
            Optional<Member> member = template.query(sql, new MemberRowMapper(), id).stream().findAny();
            return Optional.ofNullable(member.orElseThrow());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, Member member) {
        String sql = "UPDATE MEMBER SET user_name = ?, email = ?, password = ? WHERE id = ?";
        template.update(sql,
                member.getUserName(),
                member.getEmail(),
                member.getPassword(),
                id);
    }

    public Optional<Member> findByLoginId(String userId) {
        String sql = "select id,user_name, password , email from member where user_name = ?";
        return Optional.ofNullable(template.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), userId));
    }

    private static final class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("ID"));
            member.setUserName(rs.getString("user_name"));
            member.setPassword(rs.getString("password"));
            member.setEmail(rs.getString("email"));
            member.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
            return member;
        }
    }
}
