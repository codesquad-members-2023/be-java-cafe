package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveArticle(Article article) {
        // 인덱스 중복 여부 확인
        if (findById(article.getId()).isEmpty()) {
            // 한번에 PK를 가져오는 방식으로 변경
            String sql = "INSERT INTO CAFE_ARTICLE(WRITER, TITLE, CONTENTS, cafeUserId) " +
                    "VALUES (?, ?, ?, (select id from cafe_user where name = ?))";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, article.getWriter());
                pstmt.setString(2, article.getTitle());
                pstmt.setString(3, article.getContents());
                pstmt.setString(4, article.getWriter());
                return pstmt;
            }, keyHolder);

            long id = (long) keyHolder.getKeys().get("id");
            article.setId(id);
            return true;
        }
        return false;
    }

    public boolean updateArticle(Article article) {
        // 해당 번호 게시글 존재여부 체크
        if (findById(article.getId()).isPresent()) {
            jdbcTemplate.update("update CAFE_ARTICLE set title=?, contents=?, time=? where ID=?",
                    article.getTitle(), article.getContents(), LocalDateTime.now(), article.getId());
            return true;
        }
        return false;
    }

    public boolean deleteArticle(long id) {
        // 해당 번호 게시글 존재여부 체크
        if (findById(id).isPresent()) {
            // 게시글 삭제시 댓글도 삭제 되게 변경
            String sqlReply = "UPDATE CAFE_REPLY SET deleted=true where ARTICLEID = " + id;
            String sqlArticle = "update CAFE_ARTICLE set deleted = true where ID = " + id;
            jdbcTemplate.batchUpdate(sqlReply, sqlArticle);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Article> findById(long id) {
        List<Article> result = jdbcTemplate.query("select * from cafe_article where id = ? AND deleted = false", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAllArticle() {
        List<Article> result = jdbcTemplate.query("select * from cafe_article where deleted = false ORDER BY id desc ", articleRowMapper());
        return result;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setTime(rs.getTimestamp("time"));
            return article;
        };
    }

    private RowMapper<Article> articlePKMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            rs.getLong("MAX(ID)");
            return article;
        };
    }
}
