package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class H2DBArticleRepository implements ArticleRepository{

    private final NamedParameterJdbcTemplate template;

    public H2DBArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article (writer, title, contents, createDate) " +
                "values (:writer, :title, :contents, :createDate)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        template.update(sql, param);
    }

    @Override
    public Article findById(int id) {
        return null;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }
}
