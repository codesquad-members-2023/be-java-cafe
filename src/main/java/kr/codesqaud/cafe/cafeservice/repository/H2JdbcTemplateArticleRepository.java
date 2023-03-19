package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class H2JdbcTemplateArticleRepository {

    private JdbcTemplate template;

    public H2JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);;
    }
    public void save(Article article) {
        String sql = "INSERT INTO articles (writer, title, content, create_date, reply_count) VALUES (?, ?, ?, ?, ?)";
        template.update(sql, article.getWriter(), article.getTitle(), article.getContent(), article.getCreateDate(), article.getReplyCount());
    }

    public List<Article> findAll() {
        String sql = "SELECT * FROM articles";
        return template.query(sql, (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            article.setReplyCount(rs.getInt("reply_count"));
            return article;
        });
    }

    public Article findById(int id) {
        String sql = "SELECT * FROM articles WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getInt("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            article.setReplyCount(rs.getInt("reply_count"));
            return article;
        });
    }

    public JdbcTemplate getTemplate() {
        return template;
    }
}
