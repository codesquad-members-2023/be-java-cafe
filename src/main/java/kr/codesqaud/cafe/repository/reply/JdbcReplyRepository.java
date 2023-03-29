package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcReplyRepository implements ReplyRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcReplyRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Reply reply) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);
        String sql = "insert into reply (article_id, writer, contents) values (:articleId, :replyWriter, :replyContents)";
        template.update(sql, param);
    }
    private RowMapper<Reply> rowMapperReply() {
        return (rs, rowNum) ->
                new Reply(rs.getLong("reply_id"), rs.getLong("article_id"), rs.getString("writer"),
                        rs.getString("contents"), rs.getTimestamp("write_time").toLocalDateTime());
    }
}
