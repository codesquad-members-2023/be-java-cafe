package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    Logger logger = LoggerFactory.getLogger(getClass());

    // insert를 제외한 모든 쿼리문 실행
    private final NamedParameterJdbcTemplate template;
    // 간단한 insert문 만들기
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcArticleRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
        this.jdbcInsert = new SimpleJdbcInsert(template.getJdbcTemplate())
                .withTableName("Article")
                .usingGeneratedKeyColumns("id");
    }

    // executeAndReturnKey : usingGeneratedKeyColumns가 insert할 때 아이디 값을 자동으로 만들어 주니까 지정해주는거
    @Override
    public Long save(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        return jdbcInsert.executeAndReturnKey(param).longValue();
    }

    // :id -> id라는 이름으로 매핑해준다. NamedParameterJdbcTemplate를 사용하면 쓸 수 있음
    @Override
    public Optional<Article> findById(Long id) {
        try{
            String sql = "select id, title, contents, writer, write_date from article where id = :id";
            SqlParameterSource param = new MapSqlParameterSource("id", id);
            return Optional.of(template.queryForObject(sql, param, rowMapperArticle()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Article> findAll() {
        String sql = "select id, writer, title, contents, write_date from article";
        return template.query(sql, rowMapperArticle());
    }

    @Override
    public void update(Article article) {
        String sql = "update article set title = :title, contents = :contents where id = :id ";
        SqlParameterSource param = new BeanPropertySqlParameterSource(article); // 객체를 찾아야하니까 bean
        template.update(sql,param); // template.update -> int 반환
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from article where id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id); // 하나만 찾으면되니까 map(객체 단위로 쓸 수 없으니까)
        template.update(sql,param);

    }

    // db값은 ResultSet에서 가져와야하는게 그걸 객체로 만들어줌
    private RowMapper<Article> rowMapperArticle() {
        return (rs, rowNum) ->
                new Article(rs.getLong("id"), rs.getString("title"), rs.getString("contents"),
                        rs.getString("writer"), rs.getTimestamp("write_date").toLocalDateTime());
    }

}
