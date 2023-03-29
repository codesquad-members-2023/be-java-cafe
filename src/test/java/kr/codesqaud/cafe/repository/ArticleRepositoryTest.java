package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.User;

@JdbcTest
@Sql("classpath:test-schema.sql")
class ArticleRepositoryTest {
    ArticleRepository articleRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    private Article art1;
    private Article art2;

    @BeforeEach
    public void setup() {
        articleRepository = new JdbcArticleRepository(jdbcTemplate.getDataSource());

        art1 = new Article(1, new User("poro", "123", "포로", "ngw7617@naver.com"), "글쓰기는 쉽다.", "알고보면 글쓰기는 어려울지도",
                LocalDateTime.now());
        art2 = new Article(2, new User("honux", "nux", "호녹소", "honux@naver.com"), "코딩은 쉽다.", "쉽다", LocalDateTime.now());

        articleRepository.addArticle(art1);
        articleRepository.addArticle(art2);
    }

    @Test
    @DisplayName("글 타입을 인자로 받아 Repository에 저장한다.")
    public void addArticle() {
        assertAll(() -> assertThat(articleRepository.findById(1).getWriter()).isEqualTo("poro"),
                () -> assertThat(articleRepository.findById(1).getTitle()).isEqualTo("글쓰기는 쉽다."),
                () -> assertThat(articleRepository.findById(1).getContents()).isEqualTo("알고보면 글쓰기는 어려울지도"));
    }

    @Test
    @DisplayName("역순으로 글 목록을 반환한다.")
    void getArticleList() {
        assertAll(() -> assertThat(articleRepository.getArticleList().get(1).getWriter()).isEqualTo("poro"),
                () -> assertThat(articleRepository.getArticleList().get(0).getWriter()).isEqualTo("honux"));
    }

    @Test
    @DisplayName("ID에 해당하는 인덱스의 글을 반환한다.")
    void findById() {
        assertAll(() -> assertThat(articleRepository.findById(1).getWriter()).isEqualTo("poro"),
                () -> assertThat(articleRepository.findById(2).getWriter()).isEqualTo("honux"));
    }
}
