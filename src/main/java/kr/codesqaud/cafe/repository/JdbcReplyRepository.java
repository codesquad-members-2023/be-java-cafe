package kr.codesqaud.cafe.repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.ReplyDto;

public class JdbcReplyRepository implements ReplyRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReplyRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addReply(Reply reply) {
        jdbcTemplate.update(
                "insert into replies(writer, contents, creationTime, articleId) values(?, ?, ?, ?)",
                reply.getUser().getId(),
                reply.getContents(), reply.getCreationTime(), reply.getArticleId());
    }

    @Override
    public List<ReplyDto> getReplyList(long articleId) {
        return jdbcTemplate.query(
                "select id, writer, contents, articleId, creationTime from replies where articleId=? and deleted=false order by id"
                , replyRowMapper(), articleId);

    }

    @Override
    public ReplyDto findById(long replyId, long articleId) throws ArticleInfoException {
        try {
            return jdbcTemplate.queryForObject(
                    "select id, writer, contents, articleId, creationTime from replies where articleId=? and deleted=false and id=? order by id"
                    , replyRowMapper(), articleId, replyId);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleInfoException(ArticleInfoException.INVALID_REPLY_MESSAGE,
                    ArticleInfoException.INVALID_REPLY_CODE);
        }
    }

    private RowMapper<ReplyDto> replyRowMapper() {
        return (rs, rowNum) ->
                new ReplyDto(rs.getLong("id"), rs.getString("writer"),
                        rs.getString("contents"), rs.getTimestamp("creationTime").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), rs.getLong("articleId"));

    }

    @Override
    public void deleteReply(long articleId, long replyId) {
        jdbcTemplate.update("update replies set deleted=true where articleId=? and id=?", articleId, replyId);

    }

    @Override
    public boolean validateDelete(long articleId, String userId) {
        //삭제안되어있는 상태의 내 아이디가 아닌 글을 고른다.
        int numberOfRepliesWrittenByOthers = jdbcTemplate.query(
                "select id, writer, contents, articleId, creationTime from replies where articleId=? and writer<>? and deleted=false order by id"
                , replyRowMapper(), articleId, userId).size();
        return numberOfRepliesWrittenByOthers == 0;
    }
}
