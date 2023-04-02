package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.dto.ArticleDTO;
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
    public void save(ArticleDTO articleDTO) {
        String sql = "INSERT INTO article (member_id,writer, title, content) VALUES ( ?,?, ?, ?)";
        template.update(sql, articleDTO.getId(), articleDTO.getWriter(), articleDTO.getTitle(), articleDTO.getContent());
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT id,writer, title, content,created_date,reply_count ,member_id FROM article ORDER BY id DESC;";
        return template.query(sql, new ArticleRowMapper());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM article WHERE member_id = ?;";
        template.update(sql, id);
    }

    @Override
    public void update(Article article, String title, String content) {
        Long findId = article.getId();
        String sql = "UPDATE article SET title = ?, content = ? WHERE id = ?";
        template.update(sql, title, content, findId);
    }

    @Override
    public void findReplyList() {
    }

    @Override
    public Optional<Article> findById(Long id) {
        String sql = "SELECT id,writer, title, content,created_date,reply_count,member_id FROM article WHERE id = ?";
        try {
            return template.query(sql, new ArticleRowMapper(), id).stream().findAny();
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
            article.setUserId(rs.getLong("member_id"));
            article.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            article.setReplyCount(rs.getInt("reply_count"));
            return article;
        }
    }
}
