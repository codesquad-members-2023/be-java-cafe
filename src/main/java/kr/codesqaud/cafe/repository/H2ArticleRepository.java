package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.article.ArticleListResponse;
import kr.codesqaud.cafe.dto.article.ArticleResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class H2ArticleRepository implements ArticleRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2ArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "INSERT INTO ARTICLE (TITLE, CONTENTS, user_id) VALUES (:title, :body, :user_id)";
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
    public void delete(long id) {
        String sql = "DELETE FROM ARTICLE WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, param);
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
           Article article = new BeanPropertyRowMapper<>(Article.class).mapRow(rs, rowNum);
           Member writer = new BeanPropertyRowMapper<>(Member.class).mapRow(rs, rowNum);

           article.setId(rs.getLong("article_id"));
           article.setCreatedDate(rs.getTimestamp("article_createddate").toLocalDateTime());
           article.setUpdatedDate(rs.getTimestamp("article_updateddate").toLocalDateTime());

           writer.setId(rs.getLong("member_id"));
           article.setWriter(writer);

           return article;
        }

    }
}
