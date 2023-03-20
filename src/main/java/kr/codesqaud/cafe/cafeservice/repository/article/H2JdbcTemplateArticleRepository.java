package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class H2JdbcTemplateArticleRepository implements ArticleRepository {

    private JdbcTemplate template;

    public H2JdbcTemplateArticleRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO articles (writer, title, content) VALUES ( ?, ?, ?)";
        template.update(sql, article.getWriter(), article.getTitle(), article.getContent());
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT * FROM articles";
        return template.query(sql, new ArticleRowMapper());
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "SELECT * FROM articles WHERE id = ?";
        try {
            return  template.query(sql, new ArticleRowMapper(), id).stream().findAny();
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            article.setReplyCount(rs.getInt("reply_count"));
            return article;
        }
    }

    public JdbcTemplate getTemplate() {
        return template;
    }
}
