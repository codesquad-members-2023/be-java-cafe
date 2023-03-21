package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO ARTICLE (TITLE, BODY, WRITER) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, article.getTitle(), article.getContents(), article.getWriter());
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "SELECT ID, TITLE, BODY, WRITER, CREATED_AT, UPDATED_AT FROM ARTICLE WHERE ID = ?";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), id).stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT ID, TITLE, BODY, WRITER, CREATED_AT, UPDATED_AT FROM ARTICLE ORDER BY ID DESC";
        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
           Article article = new Article();
           article.setId(rs.getLong("ID"));
           article.setTitle(rs.getString("TITLE"));
           article.setContents(rs.getString("BODY"));
           article.setWriter(rs.getString("WRITER"));
           article.setCreatedDate(rs.getTimestamp("CREATED_AT"));
           article.setUpdatedDate(rs.getTimestamp("UPDATED_AT"));
           return article;
        }
    }
}
