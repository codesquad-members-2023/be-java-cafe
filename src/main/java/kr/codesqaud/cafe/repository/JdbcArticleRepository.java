package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void save(Article article) {
        String sql = "insert into article(writer, title, content) values(?, ?, ?)";
        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContent());
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public Article findByIndex(int index) {
        return null;
    }
}
