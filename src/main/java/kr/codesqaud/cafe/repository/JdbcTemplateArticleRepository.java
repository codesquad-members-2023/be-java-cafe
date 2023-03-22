package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository{
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveArticle(Article article) {
        // 인덱스 중복 여부 확인
        if (findById(article.getId()).isEmpty()) {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
            jdbcInsert.withTableName("cafe_article").usingGeneratedKeyColumns("id");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("writer", article.getWriter());
            parameters.put("title", article.getTitle());
            parameters.put("contents", article.getContents());
            parameters.put("time", LocalDateTime.now());

            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
            article.setId(key.longValue());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Article> findById(long id) {
        List<Article> result = jdbcTemplate.query("select * from cafe_article where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAllArticle() {
        List<Article> result = jdbcTemplate.query("select * from cafe_article ORDER BY id desc ", articleRowMapper());
        return result;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setTime(rs.getTimestamp("time"));
            return article;
        };
    }
}
