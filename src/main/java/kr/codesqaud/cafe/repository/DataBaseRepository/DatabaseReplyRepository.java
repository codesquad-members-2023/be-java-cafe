package kr.codesqaud.cafe.repository.DataBaseRepository;

import kr.codesqaud.cafe.basic.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class DatabaseReplyRepository {
    private static final Logger logger = LoggerFactory.getLogger("article database");

    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseReplyRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int save(Reply reply) {
        String sql = "insert into replies(articleId, writer, content) values (?, ?, ?)";

        return jdbcTemplate.update(sql, reply.getArticleId(), reply.getWriter(), reply.getContent());
    }

    public int update(Reply reply) {
        String sql = "update replies set content = ? where replyId = ?";

        return jdbcTemplate.update(sql, reply.getContent(), reply.getReplyId());
    }

    public int delete(Reply reply) {
        String sql = "delete from REPLIES where replyId=?";

        return jdbcTemplate.update(sql, reply.getReplyId());
    }

    public List<Reply> findByArticleId(int articleId) {
        String sql = "select replyId, articleId, writer, content, createAt from replies where articleId = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reply.class), articleId);
    }

    public Optional<Reply> findByReplyId(int replyId) {
        String sql = "select replyId, articleId, writer, content, createAt from replies where replyId = ? ORDER BY createAt";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Reply.class), replyId));
    }

    public List<Reply> findAll() {
        String sql = "select replyId, articleId, writer, content, createAt from replies";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reply.class));
    }

}
