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

    @Override
    public Optional<Reply> findOne(Long id) {
        try {
            String sql = "select reply_id,article_id,writer,contents,write_time from reply where reply_id = :replyId";
            SqlParameterSource param = new MapSqlParameterSource("replyId", id);
            return Optional.of(template.queryForObject(sql, param, rowMapperReply()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findAll(Long id) {
        String sql = "select reply_id,article_id,writer,contents,write_time from reply where article_id = :articleId order by write_time";
        SqlParameterSource param = new MapSqlParameterSource("articleId", id);
        return template.query(sql, param, rowMapperReply());
    }

    @Override
    public void update(Reply reply) {

    }

    @Override
    public void delete(Long id) {
        String sql = "delete from reply where reply_id = :replyId";
        SqlParameterSource param = new MapSqlParameterSource("replyId", id); // 하나만 찾으면되니까 map(객체 단위로 쓸 수 없으니까)
        template.update(sql,param);
    }

    private RowMapper<Reply> rowMapperReply() {
        return (rs, rowNum) ->
                new Reply(rs.getLong("reply_id"), rs.getLong("article_id"), rs.getString("writer"),
                        rs.getString("contents"), rs.getTimestamp("write_time").toLocalDateTime());
    }
}
