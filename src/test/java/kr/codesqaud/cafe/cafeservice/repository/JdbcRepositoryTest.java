package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcRepositoryTest {

    Logger log = LoggerFactory.getLogger(JdbcRepositoryTest.class);
    DataSource dataSource;
    H2JdbcTemplateArticleRepository repository;

    @BeforeEach
    void before() {
        dataSource = new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/cafe", "sa", "");
        repository = new H2JdbcTemplateArticleRepository(dataSource);
    }

    @Test
    @DisplayName("jdbc 커넥션 테스트")
    void connection() {
        String connection = repository.getTemplate().queryForObject("SELECT 'Connected'", String.class);
        System.out.println(connection);
        assertNotNull(connection);
    }

    @Test
    @DisplayName("Article 저장")
    void save() throws SQLException {
        Article article = new Article("작성자", "제목", "내용");
        assertNotNull(article.getId());
    }

    @Test
    @DisplayName("Article태이블 출력")
    void select() throws SQLException {
        Article article = new Article("작성자", "제목", "내용");
        repository.save(article);
        System.out.println(article.toString());
        assertNotNull(article.getId());
    }
}
