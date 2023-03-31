package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Primary
@Repository
public class DBArticleRepository implements ArticleRepository {

    private Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article (writer, title, contents) values(?, ?, ?)";

        jdbcTemplate.update(sql, article.getWriter(), article.getTitle(), article.getContents());
    }

    @Override
    public List<Article> findAllArticles() {
        String sql = "select id, writer, title, createdAt from article";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public Article findByArticleId(long id) {
        String sql = "select id, writer, title, contents, createdAt from article where id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Article.class), id);
    }

    @Override
    public void updateArticle(long articleId, String title, String contents) {
        String sql = "update article set title = ?, contents = ? where id = ?";

        jdbcTemplate.update(sql, title, contents, articleId);
    }


    @Override
    public void deleteArticle(long id) {
        String sql = "delete from article where id = ?";

        jdbcTemplate.update(sql, id);
    }
}
