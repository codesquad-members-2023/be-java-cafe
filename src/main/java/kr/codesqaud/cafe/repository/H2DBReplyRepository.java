package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Reply;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
}
