package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@Primary
public class MySQLArticleRepository implements ArticleRepository {

    private final NamedParameterJdbcTemplate template;

    public MySQLArticleRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Article article) {
        String sql = "insert into article (title, contents, createDate, deleted, user_id) " +
                "values (:title, :contents, :createDate, false, :userId)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        template.update(sql, param);
    }

    @Override
    public ArticleWithWriter findById(int id) {
        String sql = "select a.id, a.title, a.contents, a.createDate, a.user_id, u.user_id as writer, " +
                "(select count(*) from reply r where a.id=r.article_id and r.deleted=false) as replyCount " +
                "from article a join users u on a.user_id=u.id " +
                "where a.id=:id and a.deleted=false";

        try {
            Map<String, Integer> param = Map.of("id", id);
            ArticleWithWriter article = template.queryForObject(sql, param, BeanPropertyRowMapper.newInstance(ArticleWithWriter.class));
            return article;
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게시글입니다!");
        }
    }

    @Override
    public List<SimpleArticleWithWriter> findAll() {
        String sql = "select a.id, a.title, a.createDate, a.user_id, u.user_id as writer, " +
                "(select count(*) from reply r where a.id=r.article_id and r.deleted=false) as replyCount " +
                "from article a join users u on a.user_id=u.id " +
                "where a.deleted=false order by a.id desc";

        return template.query(sql, BeanPropertyRowMapper.newInstance(SimpleArticleWithWriter.class));
    }

    @Override
    @Transactional
    public void delete(int id) {
        String softDeleteReplySql = "update reply set deleted=true where article_id=:id";
        String softDeleteArticleSql = "update article set deleted=true where id=:id";

        Map<String, Integer> param = Map.of("id", id);
        template.update(softDeleteReplySql, param);
        template.update(softDeleteArticleSql, param);
    }

    @Override
    public void update(int id, Article updateArticle) {
        String sql = "update article set title=:title, contents=:contents where id=:id";

        Map<String, Object> param = Map.of("id", id, "title", updateArticle.getTitle(), "contents", updateArticle.getContents());

        template.update(sql, param);
    }
}
