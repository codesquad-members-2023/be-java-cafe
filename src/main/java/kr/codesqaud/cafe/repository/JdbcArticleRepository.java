package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return jdbcTemplate.query("select * from article", articleRowMapper());
    }

    @Override
    public Article findByIndex(int index) {
        return null;
    }

    private RowMapper<Article> articleRowMapper() { //sql 결과를 받기위해 row mapping 필요
        return new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Article(rs.getString("writer"), rs.getString("title"), rs.getString("content"), rs.getInt("index"));
            }
        };
    }
}
