package kr.codesqaud.cafe.repository;

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

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.JdbcArticleRepository;


@SpringBootTest
class ArticleRepositoryTest {

    private DataSource dataSource;
    ArticleRepository articleRepository;
    JdbcTemplate jdbcTemplate;
    private Article art1;
    private Article art2;

    @BeforeEach
    public void setup() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        articleRepository = new JdbcArticleRepository(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        art1 = new Article( "poro", "글쓰기는 쉽다.", "알고보면 글쓰기는 어려울지도");
        art2 = new Article( "honux", "코딩은 쉽다.", "쉽다");

        articleRepository.addArticle(art1);
        articleRepository.addArticle(art2);
    }

    @AfterEach
    public void clean() {
        jdbcTemplate.update("delete from articles; alter table articles alter column id restart with 1;");
    }

    @Test
    @DisplayName("글 타입을 인자로 받아 Repository에 저장한다.")
    public void addArticle() {
        assertAll(() -> assertThat(art1.getWriter()).isEqualTo("poro"),
                () -> assertThat(art1.getTitle()).isEqualTo("글쓰기는 쉽다."),
                () -> assertThat(art1.getContents()).isEqualTo("알고보면 글쓰기는 어려울지도"));
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
        assertAll(() -> assertThat(articleRepository.findById(1).get().getWriter()).isEqualTo("poro"),
                () -> assertThat(articleRepository.findById(2).get().getWriter()).isEqualTo("honux"));
    }
}
