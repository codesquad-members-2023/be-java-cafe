package kr.codesqaud.cafe.repository.comment;

import kr.codesqaud.cafe.domain.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Primary
@Repository
public class DBCommentRepository implements CommentRepository {

    private Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBCommentRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void save(Comment comment) {
        String sql = "insert into comment (articleId, writer, contents) values(?, ?, ?)";

        jdbcTemplate.update(sql, comment.getArticleId(), comment.getWriter(), comment.getContents());
    }

    @Override
    public List<Comment> findAllCommentsByArticleId(long articleId) {
        String sql = "select commentId, articleId, writer, contents, createdAt from comment where articleId = ? and deletedAt is null";

        log.info("댓글 목록 가져오기 실행");
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), articleId);
    }

    @Override
    public int findNumOfCommentsByArticleId(long articleId) {
        String sql = "select count(0) as numOfComments from comment where articleId = ? and deletedAt is null";

        return jdbcTemplate.queryForObject(sql, Integer.class, articleId);
    }

    @Override
    public Comment findByCommentId(long commentId) {
        String sql = "select commentId, articleId, writer, contents, createdAt from comment where commentId = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), commentId);
    }

    @Override
    public void deleteComment(long commentId, LocalDateTime now) {
        String sql = "update comment set deletedAt = ? where commentId = ?";

        jdbcTemplate.update(sql, now, commentId);

    }
}
