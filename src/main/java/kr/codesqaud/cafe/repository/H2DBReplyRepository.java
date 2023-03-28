package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class H2DBReplyRepository {

    private final NamedParameterJdbcTemplate template;

    public H2DBReplyRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(Reply reply) {
        String sql = "insert into reply (contents, createDate, user_id, article_id) " +
                "values (:contents, :createDate, :userId, :articleId)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);

        template.update(sql, param);
    }

    public List<ReplyWithUser> findAll() {
        String sql = "select r.id, r.user_id, " +
                "(select u.user_id from users u where r.user_id=u.id) as userName, " +
                "r.contents, r.createDate " +
                "from reply r join article a on r.article_id=a.id";

        return template.query(sql, BeanPropertyRowMapper.newInstance(ReplyWithUser.class));
    }
}
