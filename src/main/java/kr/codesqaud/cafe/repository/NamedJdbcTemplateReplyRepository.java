package kr.codesqaud.cafe.repository;


import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.dto.ReplyDto;
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
		System.out.println("--> 댓글 작성자 번호: "+reply.getUserSequence());
	}

	public List<ReplyDto> showAllReplies(Long articleSequence) {
		String sql = "select r.reply_article_number, r.reply_writer, r.reply_member_number, a.member_number, r.reply_contents, r.reply_writtentime from reply r join article a on r.reply_article_number=a.article_number where r.reply_article_number=:articleSequence";
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource("articleSequence", articleSequence);
		return template.query(sql, sqlParameterSource, replyDtoRowMapper());
	}

	private RowMapper<ReplyDto> replyDtoRowMapper() {
		return (rs, rowNum) -> {
			ReplyDto replyDto = new ReplyDto();
			replyDto.setWriter(rs.getString("reply_writer"));
			replyDto.setUserSequence(rs.getLong("reply_member_number"));
			replyDto.setContents(rs.getString("reply_contents"));
			replyDto.setWrittenTime(rs.getTimestamp("reply_writtentime").toLocalDateTime());
			replyDto.setArticleSequence(rs.getLong("reply_article_number"));
			return replyDto;
		};
	}
}
