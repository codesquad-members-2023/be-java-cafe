package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class H2JDBCReplyRepository implements ReplyRepository {

    private final JdbcTemplate template;

    @Autowired
    public H2JDBCReplyRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Reply reply) {
        String sql = "insert into reply (articleId, userId, contents, timestamp, deleted) values (?,?,?,?, ?)";

        template.update(sql, reply.getArticleId(), reply.getUserId(), reply.getContents(), new Timestamp(System.currentTimeMillis()), false);
    }

    @Override
    public List<Reply> findAllReplyByArticleId(int articleId) {
        String sql = "select * from reply where articleid=? and deleted = false";

        return template.query(sql, new BeanPropertyRowMapper<>(Reply.class), articleId);
    }
}
