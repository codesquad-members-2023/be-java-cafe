package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveArticle(Article article) {
        // 인덱스 중복 여부 확인
        if (findByIndex(article.getIndex()).isEmpty()) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("cafe_article").usingGeneratedKeyColumns("index");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("writer", article.getWriter());
            parameters.put("title", article.getTitle());
            parameters.put("contents", article.getContents());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            article.setIndex(key.longValue());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Article> findByIndex(long index) {
        List<Article> result = jdbcTemplate.query("select * from cafe_article where index = ?", articleRowMapper(), index);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAllArticle() {
        return jdbcTemplate.query("select * from cafe_article", articleRowMapper());
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setIndex(rs.getLong("index"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            return article;
        };
    }
}
