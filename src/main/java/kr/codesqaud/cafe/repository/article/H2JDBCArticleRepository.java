package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
public class H2JDBCArticleRepository implements ArticleRepository{

    private final JdbcTemplate template;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public H2JDBCArticleRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article (id, writer, title, contents, timestamp) values(?, ?, ?, ?, ?)";

        template.update(sql, findDbSize() + 1, article.getWriter(), article.getTitle(), article.getContents(), Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public Article findArticleById(int id) {
        String sql = "select * from article where id=?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Article.class), id);
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "SELECT * FROM ARTICLE";

        return template.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }

    public void updateArticle(String title, String contents, int articleId) {
        String sql = "update article set title=?, contents=? where id=?";

        template.update(sql, title, contents, articleId);
    }



    private int findDbSize() {
        String sql = "select count(id) as row_count from article";
        return template.queryForObject(sql, Integer.class);
    }

}
