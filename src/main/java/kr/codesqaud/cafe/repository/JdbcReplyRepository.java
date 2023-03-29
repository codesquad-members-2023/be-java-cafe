package kr.codesqaud.cafe.repository;

import static kr.codesqaud.cafe.exceptions.ArticleInfoException.*;

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
        final String insertReply = "insert into replies(writer, contents, creationTime, articleId) values(?, ?, ?, ?)";
        jdbcTemplate.update(insertReply, reply.getUserId(), reply.getContents(), reply.getCreationTime(),
                reply.getArticleId());
    }

    @Override
    public List<ReplyDto> getReplyList(long articleId) {
        final String selectRepliesInArticle = "select id, writer, contents, articleId, creationTime from replies where articleId=? and deleted=false order by id";
        return jdbcTemplate.query(selectRepliesInArticle, replyRowMapper(), articleId);

    }

    @Override
    public ReplyDto findById(long replyId, long articleId) throws ArticleInfoException {
        final String findReplyById = "select id, writer, contents, articleId, creationTime from replies where articleId=? and deleted=false and id=? order by id";
        try {
            return jdbcTemplate.queryForObject(findReplyById, replyRowMapper(), articleId, replyId);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleInfoException(INVALID_REPLY_MESSAGE, INVALID_REPLY_CODE);
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
        final String deleteReply = "update replies set deleted=true where articleId=? and id=?";
        jdbcTemplate.update(deleteReply, articleId, replyId);
    }

    @Override
    public boolean validateDelete(long articleId, String userId) {
        final String countRepliesByOthers = "select count(id) from replies where articleId=? and writer<>? and deleted=false order by id";
        //삭제안되어있는 상태의 내 아이디가 아닌 글을 고른다.
        int numberOfRepliesWrittenByOthers = jdbcTemplate.query(countRepliesByOthers, replyRowMapper(), articleId,
                userId).size();
        return numberOfRepliesWrittenByOthers == 0;
    }
}
