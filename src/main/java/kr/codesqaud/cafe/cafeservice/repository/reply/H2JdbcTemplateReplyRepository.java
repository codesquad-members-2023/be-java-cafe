package kr.codesqaud.cafe.cafeservice.repository.reply;

import kr.codesqaud.cafe.cafeservice.domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class H2JdbcTemplateReplyRepository implements ReplyRepository {

    private final JdbcTemplate template;

    @Autowired
    public H2JdbcTemplateReplyRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void save(Reply reply) {
        String sql = "insert into reply (member_id, article_id, content, reply_date, deleted,nickName ) values (?,?,?,?,?,?)";
        template.update(sql, reply.getMember_id(), reply.getArticle_id(), reply.getContent(), LocalDateTime.now(), false, reply.getNickName());
    }

    @Override
    public Optional<Reply> findById(Long replyId) {
        String sql = "select * from reply where id = ?";
        return Optional.ofNullable(template.queryForObject(sql, new BeanPropertyRowMapper<>(Reply.class), replyId));
    }

    @Override
    public List<Reply> findAll(Long id) {
        String sql = "SELECT * FROM reply where article_id = ? and reply.deleted = false";
        return template.query(sql, new BeanPropertyRowMapper<>(Reply.class), id);
    }

    @Override
    public void update() {

    }

    @Override
    public void delete(Long id) {
        String sql = "update reply set deleted = true where article_id = ?";
        template.update(sql, id);
    }

    @Override
    public Optional<Reply> findByWithArticle(Long articleId, Long id) {
        String sql = "select * from reply where article_id=? and DELETED  = false and id = ?";
        return Optional.ofNullable(template.queryForObject(sql, new BeanPropertyRowMapper<>(Reply.class), articleId, id));
    }
}
