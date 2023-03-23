package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource datasource) {
        jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public Member saveMember(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", member.getEmail());
        parameters.put("nickName", member.getNickName());
        parameters.put("password", member.getPassword());
        parameters.put("signUpDate", Timestamp.valueOf(member.getSignUpDate().format(DateTimeFormatter.ofPattern("yyy-MM-dd")) + " 00:00:00"));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setEmail(key.toString());
        return member;
    }

    @Override
    public Member findOneMemberbyEmail(String email) {
        return jdbcTemplate.queryForObject("select * from member where email = ?", memberRowMapper(), email);
    }
    @Override
    public Member findOneMemberbyNickName(String nickName) {
        return jdbcTemplate.queryForObject("select * from member where nickName = ?", memberRowMapper(), nickName);
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void editeMember(Member member) {
        jdbcTemplate.update("UPDATE member SET nickName = ? WHERE email = ?", member.getNickName(), member.getEmail());

    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setEmail(rs.getString("email"));
            member.setNickName(rs.getString("nickName"));
            member.setPassword(rs.getString("password"));
            member.setSignUpDate(rs.getTimestamp("signUpDate").toLocalDateTime());
            return member;
        };
    }
}
