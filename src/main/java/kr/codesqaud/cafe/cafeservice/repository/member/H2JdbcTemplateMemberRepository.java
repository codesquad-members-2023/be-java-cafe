package kr.codesqaud.cafe.cafeservice.repository.member;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
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
        try {
            String sql = "INSERT INTO MEMBER (userName, EMAIL, PASSWORD,nickName ) VALUES (?, ?, ?,?)";
            template.update(sql,
                    member.getUserName(),
                    member.getEmail(),
                    member.getPassword(),
                    member.getNickName());
        } catch (DuplicateKeyException e) {
            // 중복된 키 예외 처리
            // 예외 처리 코드를 작성합니다.
            throw new MemberNotFoundException("이미 존재하는 회원입니다.");
        }

    }

    @Override
    public String findUserName(String userName) {
        String sql = "SELECT userName FROM MEMBER WHERE userName = ?";
        Member member = template.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), userName);
        return member.getNickName();
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, userName, password ,email,nickName FROM MEMBER";
        return template.query(sql, new MemberRowMapper());
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT  id, userName, password,email,nickName FROM MEMBER WHERE ID = ?";
        try {
            Optional<Member> member = template.query(sql, new MemberRowMapper(), id).stream().findAny();
            return Optional.ofNullable(member.orElseThrow());
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, Member member) {
        String sql = "UPDATE MEMBER SET email = ?, password = ? ,nickName= ? where id = ?";
        template.update(sql,
                member.getEmail(),
                member.getPassword(),
                member.getNickName(),
                id);
    }

    public Optional<Member> findByLoginId(String userId) {
        String sql = "select * from member where userName= ?";
        try {
            Member member = template.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class), userId);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }


    private static final class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getLong("ID"));
            member.setUserName(rs.getString("userName"));
            member.setPassword(rs.getString("password"));
            member.setNickName(rs.getString("nickName"));
            member.setEmail(rs.getString("email"));
            return member;
        }
    }
}
