package kr.codesqaud.cafe.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import kr.codesqaud.cafe.repository.JdbcArticleRepository;
import kr.codesqaud.cafe.service.impl.QnaServiceImpl;
import kr.codesqaud.cafe.model.Article;

@SpringBootTest
class QnaServiceTest {
    private DataSource dataSource;
    QnaService qnaService;
    JdbcTemplate jdbcTemplate;

    private Article art1;
    private Article art2;

    @BeforeEach
    public void setup() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        qnaService = new QnaServiceImpl(new JdbcArticleRepository(dataSource));
        jdbcTemplate = new JdbcTemplate(dataSource);
        art1 = new Article( "poro", "글쓰기는 쉽다.", "알고보면 글쓰기는 어려울지도");
        art2 = new Article( "honux", "코딩은 쉽다.", "쉽다");
        qnaService.postQna(art1);
        qnaService.postQna(art2);
    }

    @AfterEach
    public void clean() {
        jdbcTemplate.update("delete from articles; alter table articles alter column id restart with 1;");
    }
    @Test
    @DisplayName("Article을 Repository에 게시한다.")
    void postQna() {
        Article article = new Article("poro", "1", "2");
        qnaService.postQna(article);
        assertThat(qnaService.lookupAllArticles().get(0).getWriter()).isEqualTo("poro");
    }

    @Test
    @DisplayName("모든 Article의 목록을 반환한다.")
    void lookupAllArticles() {
        assertAll(() -> assertThat(qnaService.lookupAllArticles().get(0).getWriter()).isEqualTo("honux"),
                () -> assertThat(qnaService.lookupAllArticles().get(1).getWriter()).isEqualTo("poro"));
    }

    @Test
    @DisplayName("ID에 해당하는 인덱스의 글을 반환한다.")
    void lookupById() {

        assertAll(() -> assertThat(qnaService.lookupById(1).getWriter()).isEqualTo("poro"),
                () -> assertThat(qnaService.lookupById(2).getWriter()).isEqualTo("honux"));
    }
}
