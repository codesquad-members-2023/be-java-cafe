package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.Reply;
import kr.codesqaud.cafe.domain.article.Writer;
import kr.codesqaud.cafe.dto.ArticleListResponse;
import kr.codesqaud.cafe.dto.ArticleResponse;
import kr.codesqaud.cafe.dto.ReplyResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MysqlArticleRepository implements ArticleRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MysqlArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO ARTICLE (TITLE, CONTENTS, USER_ID) VALUES (:title, :body, :user_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", article.getTitle())
                .addValue("body", article.getContents())
                .addValue("user_id", article.getWriterId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public ArticleResponse findById(long id) {
        String sql = "SELECT article.ID as article_index, article.TITLE, article.CONTENTS, article.CREATED_AT as article_created_date, " +
                "member.ID as writer_index, member.NICKNAME " +
                "FROM ARTICLE article " +
                "LEFT JOIN MEMBER member on article.USER_ID = member.ID " +
                " WHERE article.ID = :id AND article.IS_DELETED = false";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ArticleResponse.class));
    }

    @Override
    public List<ArticleListResponse> findAll() {
        String sql = "SELECT article.ID as article_id, article.TITLE, article.CREATED_AT , " +
                "member.ID as writer_id, member.NICKNAME as writer_nickname, " +
                "(SELECT COUNT(*) FROM ANSWER ANSWER WHERE ANSWER.ARTICLE_ID = article.ID AND ANSWER.IS_DELETED = false) as answer_count " +
                "FROM ARTICLE article " +
                "LEFT JOIN MEMBER member on article.USER_ID = member.ID " +
                "WHERE article.IS_DELETED = false " +
                "ORDER BY article.ID DESC ;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleListResponse.class));
    }

    @Override
    public void update(long index, Article newArticle) {
        String sql = "UPDATE ARTICLE SET TITLE = :title, CONTENTS = :contents, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", newArticle.getTitle())
            .addValue("contents", newArticle.getContents())
            .addValue("id", index)
            .addValue("updated", Timestamp.valueOf(LocalDateTime.now()));

        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM ARTICLE WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public long saveReply(Reply answer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO ANSWER (contents, user_id, article_id, created_at, UPDATED_AT)" +
                "VALUES (:contents, :user_id, :article_id, :created, :updated)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("contents", answer.getContents())
                .addValue("user_id", answer.getWriterId())
                .addValue("article_id", answer.getArticleId())
                .addValue("created", LocalDateTime.now())
                .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return Long.parseLong(String.valueOf(keyHolder.getKeyList().get(0).get("GENERATED_KEY")));
    }

    @Override
    public List<ReplyResponse> findReplyByArticleId(long articleId) {
        String sql = "SELECT answer.ID as answer_index, answer.CONTENTS, answer.CREATED_AT as created_date, " +
                "member.ID as writer_index, member.NICKNAME " +
                "FROM ANSWER answer LEFT JOIN MEMBER member on member.ID = answer.USER_ID " +
                "WHERE ARTICLE_ID = :articleId AND answer.IS_DELETED = false " +
                "ORDER BY answer.ID ";
        MapSqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(ReplyResponse.class));
    }

    @Override
    public ReplyResponse findReplyById(long id) {
        String sql = "SELECT A.ID as answer_index, A.CONTENTS, A.CREATED_AT as created_date, " +
                "M.ID as writer_index, M.NICKNAME " +
                "FROM ANSWER A LEFT JOIN MEMBER M on M.ID = A.USER_ID " +
                "WHERE A.ID = :id AND A.IS_DELETED = false";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new BeanPropertyRowMapper<>(ReplyResponse.class));
    }

    @Override
    public void updateReply(long exAnswerId, String newContents) {
        String sql = "UPDATE ANSWER SET CONTENTS = :contents, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", exAnswerId)
                .addValue("contents", newContents)
                .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAReply(long id) {
        String sql = "DELETE FROM ANSWER WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteAllReply(Long articleId) {
        String sql = "DELETE FROM ANSWER WHERE ARTICLE_ID = :articleId";
        MapSqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        namedParameterJdbcTemplate.update(sql, param);
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
           Article article = new BeanPropertyRowMapper<>(Article.class).mapRow(rs, rowNum);
           Writer writer = new BeanPropertyRowMapper<>(Writer.class).mapRow(rs, rowNum);

           article.setId(rs.getLong("article_id"));
           article.setCreatedDate(rs.getTimestamp("article_createddate").toLocalDateTime());
           article.setUpdatedDate(rs.getTimestamp("article_updateddate").toLocalDateTime());

           writer.setId(rs.getLong("member_id"));
           article.setWriter(writer);

           return article;
        }

    }
}
