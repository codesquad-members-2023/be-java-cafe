package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryArticleRepositoryTest {

    private final MemoryArticleRepository repository = new MemoryArticleRepository();
    @Test
    @DisplayName("Article 저장이 저장소에 제대로 되는지 확인")
    void save() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);

        List<Article> articles = repository.findAll();

        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Article 아이디로 저장소에서 검색할 수 있는지 확인")
    void findById() {
        Article article1 = new Article("Hyun", "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article("Yoon", "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        repository.save(article1);
        repository.save(article2);

        Article find = repository.findById(1);

        assertThat(find).isEqualTo(article1);
    }

}
