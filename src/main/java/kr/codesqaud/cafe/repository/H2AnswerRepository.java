package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.dto.AnswerViewDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class H2AnswerRepository implements AnswerRepository{

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public H2AnswerRepository(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Answer answer) {
        String sql = "INSERT INTO answer (contents, user_id, article_id, created_at, UPDATED_AT)" +
                "VALUES (:contents, :user_id, :article_id, :created, :updated)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("contents", answer.getContents())
            .addValue("user_id", answer.getWriterId())
            .addValue("article_id", answer.getArticleId())
            .addValue("created", LocalDateTime.now())
            .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params);
    }
