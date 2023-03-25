package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO ARTICLE (TITLE, BODY, user_id) VALUES (:title, :body, :user_id)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", article.getTitle())
                .addValue("body", article.getContents())
                .addValue("user_id", article.getWriterId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Article findById(Long id) {
        String sql = "SELECT ID, TITLE, BODY, USER_ID, CREATED_AT, UPDATED_AT FROM ARTICLE WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleRowMapper());
    }

    @Override
    public List<Article> findAll() {
        String sql = "SELECT A.ID, A.TITLE, A.BODY, A.CREATED_AT, A.UPDATED_AT, " +
                "M.ID, M.USERID, M.NICKNAME, M.EMAIL, M.PASSWORD, M.CREATED_AT, M.UPDATED_AT" +
                " FROM ARTICLE A LEFT JOIN MEMBER M" +
                " on A.USER_ID = M.ID" +
                " ORDER BY A.ID DESC;";
        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
           Article article = new BeanPropertyRowMapper<>(Article.class).mapRow(rs, rowNum);
           Member member = new BeanPropertyRowMapper<>(Member.class).mapRow(rs, rowNum);
           Objects.requireNonNull(article).setWriter(member);
           return article;
        }
    }
}
