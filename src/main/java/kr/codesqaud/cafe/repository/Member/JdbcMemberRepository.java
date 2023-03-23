package kr.codesqaud.cafe.repository.Member;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {


    Logger logger = LoggerFactory.getLogger(getClass());

    // insert를 제외한 모든 쿼리문 실행
    private final NamedParameterJdbcTemplate template;
    // 간단한 insert문 만들기
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMemberRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
        this.jdbcInsert = new SimpleJdbcInsert(template.getJdbcTemplate())
                .withTableName("Member");
    }


    @Override
    public String save(Member member) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        template.update("insert into Member (userId,password,userName,email) values (:userId, :password, :userName, :email)",param);
        return member.getUserId();
    }

    @Override
    public Optional<Member> findById(String userId) {
        try {
            String sql = "select userId, password, userName, email from member where userId = :userId";
            SqlParameterSource param = new MapSqlParameterSource("userId", userId);
            return Optional.of(template.queryForObject(sql, param, rowMapperMember()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByIdAndPassword(Member member) {
        try {
            String sql = "select userId, password, userName, email from member where userId = :userId and password = :password";
            SqlParameterSource param = new BeanPropertySqlParameterSource(member);
            return Optional.of(template.queryForObject(sql, param, rowMapperMember()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }    }

    @Override
    public List<Member> findAll() {
        String sql = "select userId, password, userName, email from Member";
        return template.query(sql,rowMapperMember());
    }

    @Override
    public void update(Member member) {
        String sql = "update member set password = :password, userName = :userName, email = :email where userId =:userId";
        SqlParameterSource param = new BeanPropertySqlParameterSource(member); // 객체를 찾아야하니까 bean
        template.update(sql,param); // template.update -> int 반환
    }

    @Override
    public void delete(String userId) {
        String sql = "delete from member where userId = :userId";
        SqlParameterSource param = new MapSqlParameterSource("userId",userId);
        template.update(sql,param);
    }

    private RowMapper<Member> rowMapperMember() {
        return (rs, rowNum) ->
                new Member(rs.getString("userId"), rs.getString("password"), rs.getString("userName"),
                        rs.getString("email"));
    }

}
