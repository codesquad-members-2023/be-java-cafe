package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
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

        List<ArticleWithWriter> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Article 아이디로 저장소에서 검색할 수 있는지 확인")
    void findById() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        ArticleWithWriter find = articleRepository.findById(1);

        assertThat(find.getContents()).isEqualTo(article1.getContents());
        assertThat(find.getTitle()).isEqualTo(article1.getTitle());
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

    @Test
    @DisplayName("저장소에서 특정 id의 Article을 제거 가능")
    void delete() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        articleRepository.delete(1);

        List<ArticleWithWriter> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);

        ArticleWithWriter restArticle = articles.get(0);
        assertThat(restArticle.getContents()).isEqualTo("미안하다. 이거보여줄려고 또 어그로끌었다.");
        assertThat(restArticle.getTitle()).isEqualTo("진짜 실화냐?");
    }

    @Test
    @DisplayName("저장소가 비어있는 경우 Article을 제거할 수 없다")
    void validateEmptyDelete() {
        assertThatThrownBy(() -> {
            articleRepository.delete(1);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 게시글이 아무것도 없어 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("저장소에 존재하지 않는 Article 은 제거할 수 없다")
    void validateNoneExistDelete() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        assertThatThrownBy(() -> {
            articleRepository.delete(-1);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당하는 게시글이 없어 삭제할 수 없습니다.");

        assertThatThrownBy(() -> {
            articleRepository.delete(3);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당하는 게시글이 없어 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("저장소에서 특정 id의 Article 수정 가능")
    void update() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        Article updateArticle = new Article(2, "진짜 실화 아니었음", "미안");
        articleRepository.update(2, updateArticle);

        ArticleWithWriter article = articleRepository.findById(2);

        assertThat(article.getTitle()).isEqualTo(updateArticle.getTitle());
        assertThat(article.getContents()).isEqualTo(updateArticle.getContents());
    }
}
