package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.dto.answer.AnswerResponseDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    public List<AnswerResponseDto> findAllByArticleId(long articleId) {
        String sql = "SELECT answer.ID as answer_index, answer.CONTENTS, answer.CREATED_AT as created_date, " +
                "member.ID as writer_index, member.NICKNAME " +
                "FROM ANSWER answer LEFT JOIN MEMBER member on member.ID = answer.USER_ID " +
                "WHERE ARTICLE_ID = :articleId AND answer.IS_DELETED = false " +
                "ORDER BY answer.ID ";
        MapSqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(AnswerResponseDto.class));
    }

    @Override
    public AnswerResponseDto findById(long id) {
        String sql = "SELECT A.ID as answer_index, A.CONTENTS, A.CREATED_AT as created_date, " +
                "M.ID as writer_index, M.NICKNAME " +
                "FROM ANSWER A LEFT JOIN MEMBER M on M.ID = A.USER_ID " +
                "WHERE A.ID = :id AND A.IS_DELETED = false";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, param, new BeanPropertyRowMapper<>(AnswerResponseDto.class));
    }

    @Override
    public void update(long exAnswerId, String newContents) {
        String sql = "UPDATE ANSWER SET CONTENTS = :contents, UPDATED_AT = :updated WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", exAnswerId)
            .addValue("contents", newContents)
            .addValue("updated", LocalDateTime.now());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM ANSWER WHERE id = :id";
        MapSqlParameterSource param = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteAll(Long articleId) {
        String sql = "DELETE FROM ANSWER WHERE ARTICLE_ID = :articleId";
        MapSqlParameterSource param = new MapSqlParameterSource("articleId", articleId);
        namedParameterJdbcTemplate.update(sql, param);
    }

}
