package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class H2JDBCArticleRepository implements ArticleRepository {

    private final JdbcTemplate template;

    @Autowired
    public H2JDBCArticleRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(ArticleFormDTO article) {
        String sql = "insert into article (userid, title, contents, timestamp) values(?, ?, ?, ?)";

        template.update(sql, article.getUserId(), article.getTitle(), article.getContents(), Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public Optional<Article> findArticleById(int id) {
        String sql = "select * from article where id=?";

        return Optional.ofNullable(template.queryForObject(sql, new BeanPropertyRowMapper<>(Article.class), id));
    }

    @Override
    public List<Article> findAllArticle() {
        String sql = "select * from article order by timestamp desc";

        return template.query(sql, new BeanPropertyRowMapper<>(Article.class));
    }

    @Override
    public void updateArticle(String title, String contents, int articleId) {
        String sql = "update article set title=?, contents=? where id=?";

        template.update(sql, title, contents, articleId);
    }

    @Override
    public void deleteArticle(int articleId) {
        String sql = "delete from article where id=?";

        template.update(sql, articleId);
    }

    @Override
    public String findUsernameByArticleUserId(String userId) {
        String sql = "select member.name from article inner join member on article.userid = member.userid where article.userid = ? group by member.name";

        return template.queryForObject(sql, String.class, userId);
    }

    @Override
    public List<Reply> findAllReplyByArticleId(int articleId) {
        String sql = "select * from reply where articleid=?";

        return template.query(sql, new BeanPropertyRowMapper<>(Reply.class), articleId);
    }
}
