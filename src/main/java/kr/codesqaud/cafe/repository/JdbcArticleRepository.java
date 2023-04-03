package kr.codesqaud.cafe.repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.ArticleDto;
import kr.codesqaud.cafe.model.ArticleReplyCountsDto;

public class JdbcArticleRepository implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addArticle(Article article) {
        String insertArticle = "insert into articles(writer,title,contents,creationTime) values(?, ?, ?, ?)";
        jdbcTemplate.update(insertArticle,
                article.getUserId(), article.getTitle(), article.getContents(), article.getCreationTime());
    }

    @Override
    public void modifyArticle(long id, String title, String contents) {
        String modifyArticle = "update articles set title=?, contents=? where id=?";
        jdbcTemplate.update(modifyArticle, title, contents, id);
    }

    @Override
    @Transactional
    public void deleteArticle(long articleId) {
        final String softDeleteArticle = "update articles set deleted=true where id=?";
        final String softDeleteReplies = "update replies set deleted=true where articleId=?";
        jdbcTemplate.update(softDeleteArticle, articleId);
        jdbcTemplate.update(softDeleteReplies, articleId);
    }

    @Override
    public List<ArticleReplyCountsDto> getArticleList() {
        String getArticles = "select articles.id, articles.writer, title, articles.creationTime, count(replies.id) from articles left join replies on (articles.id=replies.articleId and replies.deleted=false)  where articles.deleted=false group by articles.id order by id desc";
        return jdbcTemplate.query(getArticles, articleReplyCountsDtoRowMapper());
    }

    @Override
    public ArticleDto findById(long id) throws ArticleInfoException {
        try {
            String findById = "select writer,title,contents,id,creationTime from articles where deleted=false and id = ?";
            return jdbcTemplate.queryForObject(findById, articleRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleInfoException(ArticleInfoException.INVALID_ARTICLE_MESSAGE,
                    ArticleInfoException.INVALID_ARTICLE_CODE);
        }
    }

    private RowMapper<ArticleDto> articleRowMapper() {
        return (rs, rowNum) ->
                new ArticleDto(rs.getLong("id"), rs.getString("writer"),
                        rs.getString("title"), rs.getString("contents"),
                        rs.getTimestamp("creationTime").toLocalDateTime()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
    }

    private RowMapper<ArticleReplyCountsDto> articleReplyCountsDtoRowMapper() {
        return (rs, rowNum) ->
                new ArticleReplyCountsDto(rs.getLong("id"), rs.getString("writer"),
                        rs.getString("title"),
                        rs.getTimestamp("creationTime").toLocalDateTime()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                        rs.getInt("count(replies.id)"));
    }
}

