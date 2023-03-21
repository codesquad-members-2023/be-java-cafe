package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        String sql = "select id, writer, title, contents, createDate from article where id=:id";

        try {
            Map<String, Integer> param = Map.of("id", id);
            Article article = template.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(Article.class));
            return article;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게시글입니다!");
        }
    }

    @Override
    public List<Article> findAll() {
        String sql = "select id, writer, title, contents, createDate from article order by id desc";

        return template.query(sql, BeanPropertyRowMapper.newInstance(Article.class));
    }
}
