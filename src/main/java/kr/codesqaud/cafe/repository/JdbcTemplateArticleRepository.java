package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveArticle(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("articleId");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", article.getAuthor());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContent());
        parameters.put("registrationDate", article.getRegistrationDate());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public Optional<Article> findOneArticleById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Article> getArticles() {
        return null;
    }

    @Override
    public void deleteArticle(Member member) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
