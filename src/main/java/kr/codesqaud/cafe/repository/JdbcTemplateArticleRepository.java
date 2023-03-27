package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateArticleRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveArticle(Article article) {
        // 인덱스 중복 여부 확인
        if (findById(article.getId()).isEmpty()) {
            // jdbcTemplate으로 변경 / PK값을 가져오는 방법이 이것뿐인가?;;;;
            jdbcTemplate.update("INSERT INTO CAFE_ARTICLE(WRITER, TITLE, CONTENTS, TIME) VALUES (?, ?, ?, ?)"
                    , article.getWriter(), article.getTitle(), article.getContents(), LocalDateTime.now());
            // 방법을 몰라서 마지막 pk값을 가져옴 ㅠ
            List<Article> lastValue = jdbcTemplate.query("SELECT MAX(ID) FROM CAFE_ARTICLE", articlePKMapper());
            article.setId(lastValue.get(0).getId());
            return true;
        }
        return false;
    }

    public boolean updateArticle(Article article) {
        // 해당 번호 게시글 존재여부 체크
        if (findById(article.getId()).isPresent()) {
            jdbcTemplate.update("update CAFE_ARTICLE set title=?, contents=?, time=? where ID=?",
                    article.getTitle(), article.getContents(), LocalDateTime.now(), article.getId());
            return true;
        }
        return false;
    }

    public boolean deleteArticle(long id) {
        // 해당 번호 게시글 존재여부 체크
        if(findById(id).isPresent()) {
            jdbcTemplate.update("DELETE CAFE_ARTICLE where ID=?", id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Article> findById(long id) {
        List<Article> result = jdbcTemplate.query("select * from cafe_article where id = ?", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAllArticle() {
        List<Article> result = jdbcTemplate.query("select * from cafe_article ORDER BY id desc ", articleRowMapper());
        return result;
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setTime(rs.getTimestamp("time"));
            return article;
        };
    }

    private RowMapper<Article> articlePKMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            rs.getLong("MAX(ID)");
            return article;
        };
    }
}
