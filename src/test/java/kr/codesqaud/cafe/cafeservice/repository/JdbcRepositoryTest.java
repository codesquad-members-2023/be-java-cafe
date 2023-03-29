package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.repository.article.H2ArticleRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class JdbcRepositoryTest {

    Logger log = LoggerFactory.getLogger(JdbcRepositoryTest.class);

    @Autowired
    private DataSource dataSource;
    H2ArticleRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new H2ArticleRepository();
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from articles;" +
                "alter table articles alter column id restart with 1";

        template.update(sql);
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void testSave() throws SQLException {
        // given
        Article article = new Article();
        article.setWriter("writer1");
        article.setTitle("title1");
        article.setContent("content1");

        // when
        repository.save(article);

        // then
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "articles", "title='title1'");
        assertEquals(1, count);
    }

    @Test
    @DisplayName("전체 게시글 조회 테스트")
    void findAll() throws SQLException {
        // given
        Article article1 = new Article();
        article1.setWriter("writer1");
        article1.setTitle("title1");
        article1.setContent("content1");
        repository.save(article1);

        Article article2 = new Article();
        article2.setWriter("writer2");
        article2.setTitle("title2");
        article2.setContent("content2");
        repository.save(article2);

        // when
        List<Article> articles = repository.findAll();

        // then
        assertEquals(article1.getTitle(), articles.get(0).getTitle());
        assertEquals(article2.getTitle(), articles.get(1).getTitle());
    }

    @Test
    @DisplayName("게시글 ID로 조회 테스트 - 존재하는 경우")
    void findById() throws SQLException {
        // given
        Article article = new Article();
        article.setWriter("writer1");
        article.setTitle("title1");
        article.setContent("content1");
        repository.save(article);
        System.out.println("findall" + repository.findAll());

        // when
        Optional<Article> result = repository.findById(article.getId());
        System.out.println(result.get().getId());
        System.out.println(result);
        System.out.println(article);
        // then
        assertEquals(article.getTitle(), result.get().getTitle());
    }

    @Test
    @DisplayName("게시글 ID로 조회 테스트 - 존재하지 않는 경우")
    void findByIdx() throws SQLException {
        // given
        Long id = 1000L;

        // when
        Optional<Article> result = repository.findById(id);

        // then
        assertTrue(result.isEmpty());
    }
}
