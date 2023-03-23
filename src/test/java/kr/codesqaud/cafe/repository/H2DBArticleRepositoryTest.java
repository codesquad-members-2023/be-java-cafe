package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class H2DBArticleRepositoryTest {

    private DataSource dataSource;
    private H2DBArticleRepository repository;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        repository = new H2DBArticleRepository(dataSource);
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from article;" +
                "alter table article alter column id restart with 1";

        template.update(sql);
    }

    @Test
    @Transactional
    @DisplayName("게시글 번호로 DB에서 단 건 조회가 가능")
    void findById() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);

        Article article = repository.findById(1);

        assertThat(article.getWriter()).isEqualTo("Hyun");
        assertThat(article.getContents()).isEqualTo("미안하다. 이거보여줄려고 어그로끌었다.");
    }

    @Test
    @Transactional
    @DisplayName("게시글을 DB 에서 모두 조회 가능")
    void findAll() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);

        List<Article> articles = repository.findAll();
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName("저장소에 존재하지 않는 Article 검색 시 예외 발생")
    void validateArticleExist() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);

        assertThatThrownBy(() -> {
            repository.findById(3);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 게시글입니다!");
    }
}
