package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.user.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository

public class NamedJdbcTemplateMemberRepository {

	private final NamedParameterJdbcTemplate template;

	// 생성자가 단 한개만 있으면 @Autowired 생략 가능
	public NamedJdbcTemplateMemberRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	public Member save(Member member) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(member);
		template.update("insert into member(member_id, member_password, member_name, member_email) values (:userId, :password, :name, :email)", sqlParameterSource);
		return member;
	}

	public Optional<Member> findById(String userId) {
		try {
			String sql = "select member_number, member_id, member_password, member_name, member_email from member where member_id=:userId";
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource("userId", userId);
			return Optional.of(template.queryForObject(sql, sqlParameterSource, memberRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<Member> findByNumber(Long userSequence) {
		try {
			String sql = "select member_number, member_id, member_password, member_name, member_email from member where member_number=:userSequence";
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource("userSequence", userSequence);
			return Optional.of(template.queryForObject(sql, sqlParameterSource, memberRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Member> showAllUsers() {
		String sql = "select member_number, member_id, member_password, member_name, member_email from member";
		return template.query(sql, memberRowMapper());
	}

	public void update(Member updateParam) {
		if (!findById(updateParam.getUserId()).orElseThrow().getPassword().equals(updateParam.getPassword())) {
			return;
		}
		String sql = "update member set member_name = :name, member_email= :email where member_id=:userId";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(updateParam);
		template.update(sql, sqlParameterSource);
	}

	private RowMapper<Member> memberRowMapper() {
		return new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member();
				member.setUserSequence(rs.getLong("member_number"));
				member.setUserId(rs.getString("member_id"));
				member.setPassword(rs.getString("member_password"));
				member.setName(rs.getString("member_name"));
				member.setEmail(rs.getString("member_email"));
				return member;
			}
		};
	}
}
