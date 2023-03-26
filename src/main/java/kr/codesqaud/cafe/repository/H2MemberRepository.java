package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class H2MemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2MemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {
        if (validMemberId(member.getNickname())) {
            String sql = "INSERT INTO MEMBER (USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT) VALUES (:userId, :nickName, :email, :password, :createdAt)";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", member.getUserId())
                .addValue("nickName", member.getNickname())
                .addValue("email", member.getEmail())
                .addValue("password", member.getPassword())
                .addValue("createdAt", Timestamp.valueOf(LocalDateTime.now()));
            namedParameterJdbcTemplate.update(sql, params);
        }
    }

    @Override
    public Member findById(Long userId) {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER WHERE ID = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new MemberRowMapper());
    }

    @Override
    public Member findByMemberId(String userId) {
        String sql = "SELECT ID, USERID, NICKNAME, EMAIL, PASSWORD, CREATED_AT, UPDATED_AT FROM MEMBER WHERE USERID = :userId";
        MapSqlParameterSource param = new MapSqlParameterSource("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new MemberRowMapper());
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
        String sql = "UPDATE MEMBER SET NICKNAME = :nickname, EMAIL = :email, PASSWORD = :password, CREATED_AT = :created, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nickname", updatedMember.getNickname())
            .addValue("email", updatedMember.getNickname())
            .addValue("password", updatedMember.getUpdatedDate())
            .addValue("created", updatedMember.getCreatedDate())
            .addValue("updated", updatedMember.getUpdatedDate())
            .addValue("id", exMember.getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    private static final class MemberRowMapper implements RowMapper<Member> {
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("ID"));
            member.setUserId(rs.getString("USERID"));
            member.setNickname(rs.getString("NICKNAME"));
            member.setPassword(rs.getString("PASSWORD"));
            member.setEmail(rs.getString("EMAIL"));
            member.setCreatedDate(rs.getTimestamp("CREATED_AT").toLocalDateTime());
            member.setUpdatedDate(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
            return member;
        }
    }
}
