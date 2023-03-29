package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class H2DBReplyRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public H2DBReplyRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public void save(Reply reply) {
        String sql = "insert into reply (contents, createDate, deleted, user_id, article_id) " +
                "values (:contents, :createDate, false, :userId, :articleId)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(reply);

        template.update(sql, param);
    }

    public Reply findById(int id) {
        String sql = "select id, contents, createDate, user_id, article_id " +
                "from reply where id=:id and deleted=false";
        Map<String, Integer> param = Map.of("id", id);

        return template.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Reply.class));
    }

    public List<ReplyWithUser> findByArticleId(int articleId) {
        String sql = "select r.id, r.user_id, u.user_id as userName, r.contents, r.createDate " +
                "from reply r join users u on r.user_id=u.id " +
                "where r.article_id=:articleId and r.deleted=false";

        Map<String, Integer> param = Map.of("articleId", articleId);
        return template.query(sql, param, BeanPropertyRowMapper.newInstance(ReplyWithUser.class));
    }

    public void delete(int id) {
        String sql = "update reply set deleted=true where id=:id";

        Map<String, Integer> param = Map.of("id", id);
        template.update(sql, param);
    }
}
