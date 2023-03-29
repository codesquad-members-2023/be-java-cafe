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

    @Override
    public List<Answer> findAll(long articleId) {
        String sql = "SELECT A.ID, A.CONTENTS, A.USER_ID, A.ARTICLE_ID, A.CREATED_AT as created_date, A.UPDATED_AT as updated_date, " +
                "M.ID as writer_id, M.NICKNAME as writer_nickname FROM ANSWER A LEFT JOIN MEMBER M on M.ID = A.USER_ID WHERE ARTICLE_ID = :articleId";
        MapSqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, param, new AnswerRowMapper());
    }

    @Override
    public Answer findById(long id) {
        String sql = "SELECT A.ID, A.CONTENTS, A.USER_ID, A.ARTICLE_ID, A.CREATED_AT, A.UPDATED_AT, " +
                "M.ID as writer_id, M.NICKNAME as writer_nickname FROM ANSWER A LEFT JOIN MEMBER M on M.ID = A.USER_ID WHERE A.ID = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new AnswerRowMapper());
    }

    @Override
    public void update(Answer exAnswer, Answer newAnswer) {
        String sql = "UPDATE ANSWER SET CONTENTS = :contents, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", exAnswer.getId())
            .addValue("contents", newAnswer.getContents())
            .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void update(Answer answer, long answerId) {
        String sql = "UPDATE ANSWER SET CONTENTS = :contents, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", answerId)
                .addValue("contents", answer.getContents())
                .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM ANSWER WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, param);
    }

    private static class AnswerRowMapper implements RowMapper<Answer> {
        @Override
        public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
            AnswerViewDto answerViewDto = new BeanPropertyRowMapper<>(AnswerViewDto.class).mapRow(rs, rowNum);
            answerViewDto.setCreatedDate(rs.getTimestamp("CREATED_AT").toLocalDateTime());
            answerViewDto.setUpdatedDate(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
            return answerViewDto.toDomain();
        }
    }
}
