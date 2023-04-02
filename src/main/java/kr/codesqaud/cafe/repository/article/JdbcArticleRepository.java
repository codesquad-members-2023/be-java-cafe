package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.domain.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class JdbcArticleRepository implements ArticleRepository {

    Logger logger = LoggerFactory.getLogger(getClass());

    // insert를 제외한 모든 쿼리문 실행
    private final NamedParameterJdbcTemplate template;

    public JdbcArticleRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    // executeAndReturnKey : usingGeneratedKeyColumns가 insert할 때 아이디 값을 자동으로 만들어 주니까 지정해주는거
    @Override
    public Long save(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);
        template.update("insert into Article (author_id, title, contents) values (:authorId, :title, :contents)", param);
        return article.getId();
    }

    // :id -> id라는 이름으로 매핑해준다. NamedParameterJdbcTemplate를 사용하면 쓸 수 있음
    @Override
    public Optional<Article> findById(Long id) {
        try{
            String sql = "select id, title, contents, author_id, write_date from article where id = :id AND deleted = false";
            SqlParameterSource param = new MapSqlParameterSource("id", id);
            return Optional.of(template.queryForObject(sql, param, rowMapperArticle()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Article> findAll() {
        String sql = "select id, author_id, title, write_date,contents from article where deleted = false order by id desc";
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
        // String sql = "delete from article where id = :id";
        String sql = "update article set deleted = true where id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id); // 하나만 찾으면되니까 map(객체 단위로 쓸 수 없으니까)
        template.update(sql,param);
    }

    // db값은 ResultSet에서 가져와야하는게 그걸 객체로 만들어줌
    private RowMapper<Article> rowMapperArticle() {
        return (rs, rowNum) ->
                new Article(rs.getLong("id"),rs.getString("author_id"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("write_date").toLocalDateTime());
    }

}
