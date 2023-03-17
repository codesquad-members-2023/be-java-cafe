package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class H2DBArticleRepositoryTest {

    private H2DBArticleRepository repository;

    @BeforeEach
    void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/projects/be-java-cafe/step-3-db", "sa", "");

        repository = new H2DBArticleRepository(dataSource);
    }

    @Test
    @DisplayName("게시글이 DB에 제대로 저장되는지 확인")
    void save() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);
    }

    @Test
    @DisplayName("게시글 번호로 단 건 조회가 가능")
    void findById() {

        Article article = repository.findById(1);

        assertThat(article.getWriter()).isEqualTo("Hyun");
        assertThat(article.getContents()).isEqualTo("미안하다. 이거보여줄려고 어그로끌었다.");
    }
}
