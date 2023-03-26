package kr.codesqaud.cafe.repository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;

public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addArticle(Article article) {
        jdbcTemplate.update("insert into articles(writer,title,contents,creationTime) values(?, ?, ?, ?)",
                article.getUser().getId(),
                article.getTitle(), article.getContents(), article.getCreationTime());
    }

    @Override
    public void modifyArticle(long id, String title, String contents) {
        jdbcTemplate.update("update articles set title=?, contents=? where id=?", title, contents, id);
    }

    @Override
    public void deleteArticle(long id) {
        jdbcTemplate.update("delete from articles where id=?", id);
    }

    @Override
    public List<ArticleDto> getArticleList() {
        return jdbcTemplate.query("select writer,title,contents,id,creationTime from articles order by id desc",
                articleRowMapper());
    }

    @Override
    public Optional<ArticleDto> findById(long id) {
        return Optional.ofNullable(
                jdbcTemplate.query("select writer,title,contents,id,creationTime from articles where id = ?",
                        articleRowMapper(), id).get(0));
    }

    private RowMapper<ArticleDto> articleRowMapper() {
        return (rs, rowNum) ->
                new ArticleDto(rs.getLong("id"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("creationTime").toLocalDateTime());
    }
}

