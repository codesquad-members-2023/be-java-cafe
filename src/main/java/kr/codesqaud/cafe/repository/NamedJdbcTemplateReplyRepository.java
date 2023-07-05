package kr.codesqaud.cafe.repository;


import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.dto.ReplyEditDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NamedJdbcTemplateReplyRepository {

	private final NamedParameterJdbcTemplate template;

	@Autowired
	public NamedJdbcTemplateReplyRepository(NamedParameterJdbcTemplate template) {
		this.template = template;
	}

	public void write(Reply reply) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reply);
		template.update("insert into reply(reply_contents, reply_writer, reply_member_number, reply_article_number) values (:contents, :writer, :userSequence, :articleSequence)", sqlParameterSource);
	}

	public List<ReplyDto> showAllReplies(Long articleSequence) {
		String sql = "select r.reply_sequence, r.reply_article_number, r.reply_writer, r.reply_member_number, a.member_number, r.reply_contents, r.reply_writtentime from reply r join article a on r.reply_article_number=a.article_number where r.reply_article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
		return template.query(sql, sqlParameterSource, replyDtoRowMapper());
	}

	public ReplyEditDto findReplyEditDtoBySequence(Long replySequence) {
		String sql = "select reply_contents, reply_member_number, reply_sequence from reply where reply_sequence=:replySequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("replySequence", replySequence);
		return template.queryForObject(sql, sqlParameterSource, replyOnlyWithContentsDtoRowMapper());
	}

	public void update(ReplyEditDto replyEditDto) {
		String sql = "update reply set reply_contents=:contents where reply_sequence=:replySequence";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(replyEditDto);
		template.update(sql, sqlParameterSource);
	}

	public void delete(ReplyEditDto replyEditDto) {
		String sql = "delete from reply where reply_sequence=:replySequence";
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(replyEditDto);
		template.update(sql, sqlParameterSource);
	}


	private RowMapper<ReplyDto> replyDtoRowMapper() {
		return (rs, rowNum) -> {
			ReplyDto replyDto = new ReplyDto();
			replyDto.setReplySequence(rs.getLong("reply_sequence"));
			replyDto.setWriter(rs.getString("reply_writer"));
			replyDto.setUserSequence(rs.getLong("reply_member_number"));
			replyDto.setContents(rs.getString("reply_contents"));
			replyDto.setWrittenTime(rs.getTimestamp("reply_writtentime").toLocalDateTime());
			replyDto.setArticleSequence(rs.getLong("reply_article_number"));
			return replyDto;
		};
	}

	private RowMapper<ReplyEditDto> replyOnlyWithContentsDtoRowMapper() {
		return (rs, rowNum) -> {
			ReplyEditDto replyEditDto = new ReplyEditDto();
			replyEditDto.setContents(rs.getString("reply_contents"));
			replyEditDto.setReplySequence(rs.getLong("reply_sequence"));
			replyEditDto.setUserSequence(rs.getLong("reply_member_number"));
			return replyEditDto;
		};
	}
}
