package kr.codesqaud.cafe.repository;

import java.util.List;
import kr.codesqaud.cafe.model.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao {
    private final JdbcTemplate jdbcTemplate;

    public ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addArticle(Article article) {
        String sql = "INSERT INTO ARTICLE(ARTICLE_ID, USER_ID, TITLE, CONTENTS, TIME, DELETED) VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                article.getArticleId(),
                article.getUserId(),
                article.getTitle(),
                article.getContents(),
                article.getTime(),
                article.isDeleted());
    }

    public List<ArticleDto> findAllArticle() {
        String sql = "SELECT * FROM ARTICLE WHERE DELETED = false";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleDto.class));
    }

    public List<ArticleDto> findArticleContentById(int articleId) {
        String sql = "SELECT * FROM ARTICLE WHERE ARTICLE_ID = ? AND DELETED = false";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleDto.class), articleId);
    }

    public void updateArticle(Article article, String articleId) {
        String sql = "UPDATE ARTICLE SET TITLE = ?, CONTENTS = ?, TIME = ? WHERE ARTICLE_ID = ? AND DELETED = false";
        jdbcTemplate.update(sql,
                article.getTitle(),
                article.getContents(),
                article.getTime(),
                articleId
        );
    }

    public void deleteArticle(int articleId) {
        String sql = "UPDATE ARTICLE SET DELETED = true WHERE ARTICLE_ID = ? AND DELETED = false";
        jdbcTemplate.update(sql,
                articleId
        );
    }
}
