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
    public Optional<Reply> findOne(Long replyId) {
        try {
            String sql = "select reply_id,article_id,writer,contents,write_time from reply where reply_id = :replyId AND deleted = false";
            SqlParameterSource param = new MapSqlParameterSource("replyId", replyId);
            return Optional.of(template.queryForObject(sql, param, rowMapperReply()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reply> findAll(Long articleId) {
        String sql = "select reply_id,article_id,writer,contents,write_time from reply where article_id = :articleId AND deleted = false order by write_time";
        SqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        return template.query(sql, param, rowMapperReply());
    }

    @Override
    public void update(Reply reply) {

    }

    @Override
    public void delete(Long replyId) {
        //String sql = "delete from reply where reply_id = :replyId";
        String sql = "update reply set deleted = true where reply_id = :replyId";
        SqlParameterSource param = new MapSqlParameterSource("replyId", replyId); // 하나만 찾으면되니까 map(객체 단위로 쓸 수 없으니까)
        template.update(sql,param);
    }

    @Override
    public void deletedAll(Long articleId){
        String sql = "update reply set deleted = true where article_id = :articleId";
        SqlParameterSource param = new MapSqlParameterSource("articleId", articleId); // 하나만 찾으면되니까 map(객체 단위로 쓸 수 없으니까)
        template.update(sql,param);
    }

    private RowMapper<Reply> rowMapperReply() {
        return (rs, rowNum) ->
                new Reply(rs.getLong("reply_id"), rs.getLong("article_id"), rs.getString("writer"),
                        rs.getString("contents"), rs.getTimestamp("write_time").toLocalDateTime());
    }
}
