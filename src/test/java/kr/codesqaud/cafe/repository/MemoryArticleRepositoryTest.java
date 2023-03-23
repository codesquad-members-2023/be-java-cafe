package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemoryArticleRepositoryTest {

    private final MemoryArticleRepository articleRepository = new MemoryArticleRepository();
    private final MemoryUserRepository userRepository = new MemoryUserRepository();

    @BeforeEach
    void init() {
        userRepository.save(new User("Hyun", "1234", "황현", "ghkdgus29@naver.com"));
        userRepository.save(new User("Yoon", "1234", "황윤", "ghkddbs28@naver.com"));
    }
    @Test
    @DisplayName("Article 저장이 저장소에 제대로 되는지 확인")
    void save() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        List<Article> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Article 아이디로 저장소에서 검색할 수 있는지 확인")
    void findById() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        Article find = articleRepository.findById(1);

        assertThat(find).isEqualTo(article1);
    }

    @Test
    @DisplayName("저장소에 존재하지 않는 Article 검색 시 예외 발생")
    void validateArticleExist() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        assertThatThrownBy(() -> {
            articleRepository.findById(3);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 게시글입니다!");
    }
}
