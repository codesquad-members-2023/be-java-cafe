package kr.codesqaud.cafe.repository;

import java.util.List;
import kr.codesqaud.cafe.model.Reply;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDao {
    private final JdbcTemplate jdbcTemplate;

    public ReplyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createReply(Reply reply) {
        String sql = "INSERT INTO REPLY(REPLY_ID, ARTICLE_ID, USER_ID, CONTENTS, TIME, DELETED) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                reply.getReplyId(),
                reply.getArticleId(),
                reply.getUserId(),
                reply.getContents(),
                reply.getTime(),
                reply.isDeleted());
    }

    public List<ReplyDto> findReplyAllByArticleId(int articleId) {
        String sql = "SELECT * FROM REPLY WHERE ARTICLE_ID = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ReplyDto.class), articleId);
    }
}
