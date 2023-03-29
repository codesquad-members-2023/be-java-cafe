package kr.codesqaud.cafe.repository.DataBaseRepository;

import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class DataBaseArticleRepository implements ArticleRepository {
    private static final Logger logger = LoggerFactory.getLogger("article database");

    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int save(Article article) {
        String sql = "insert into articles(writer, title, contents) values (?, ?, ?)";

        return jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents());
    }

    @Override
    public int update(int articleId, String title, String content) {
        String sql = "update articles set title = ?, contents = ? where articleId = ?";

        return jdbcTemplate.update(sql, title, content ,articleId);
    }

    public int delete(int articleId) {
        String sql = "delete from articles where articleId=?";

        return jdbcTemplate.update(sql, articleId);
    }

    public Optional<Article> findByArticleId(int index) {
        String sql = "select articleId, writer, title, contents, timeStamp from articles where articleId = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Article.class), index));
    }

    public List<Article> findAll() {
        String sql = "select articleId, writer, title, contents, timeStamp from articles";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }

    private int getArticleSize() throws SQLException {
        String sql = "select count(*) from articles";

        return jdbcTemplate.queryForObject(sql, Integer.TYPE);
    }
}
