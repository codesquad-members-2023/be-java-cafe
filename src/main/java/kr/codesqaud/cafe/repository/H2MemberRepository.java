package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class H2MemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2MemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {
        if (validMemberId(member.getNickname())) {
            String sql = "INSERT INTO MEMBER (USERID, NICKNAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    member.getUserId(),
                    member.getNickname(),
                    member.getEmail(),
                    member.getPassword());
        }
    }

    @Override
    public Optional<Member> findById(Long userId) {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER WHERE ID = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), userId).stream().findAny();
    }

    @Override
    public Optional<Member> findByMemberId(String userId) {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER WHERE USERID = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), userId).stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    @Override
    public boolean validMemberId(String userId) {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER WHERE USERID = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), userId).isEmpty();
    }

    @Override
    public void update(Member exMember, Member newUser) throws NoSuchElementException {
        Member updatedMember = exMember.update(newUser);
        String sql = "UPDATE MEMBER SET NICKNAME = ?, EMAIL = ?, PASSWORD = ? WHERE USERID = ?";
        jdbcTemplate.update(sql, updatedMember.getNickname(), updatedMember.getEmail(), updatedMember.getPassword(), updatedMember.getUserId());
    }

    private static final class MemberRowMapper implements RowMapper<Member> {
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("ID"));
            member.setUserId(rs.getString("USERID"));
            member.setNickname(rs.getString("NICKNAME"));
            member.setPassword(rs.getString("PASSWORD"));
            member.setEmail(rs.getString("EMAIL"));
            member.setCreatedDate(rs.getTimestamp("CREATED_AT"));
            member.setUpdatedDate(rs.getTimestamp("UPDATED_AT"));
            return member;
        }
    }
}
