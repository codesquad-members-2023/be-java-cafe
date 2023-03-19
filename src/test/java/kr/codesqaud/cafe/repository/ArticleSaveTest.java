package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.repository.article.H2DBArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class ArticleSaveTest {

    private ArticleRepository articleRepository = new H2DBArticleRepository();

    @AfterEach
    void afterEach() {
        articleRepository.delete(3);
    }

    @Test
    @DisplayName("질문글 저장 테스트")
    void articleSaveTest() {
        Article article = new Article("writer", "title", "contents", Timestamp.valueOf(LocalDateTime.now()));
        articleRepository.save(article);

        Article findArticle = articleRepository.findArticleByWriter(3);

        Assertions.assertThat(findArticle.getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("잘못된 id 입력시 예외 발생")
    void articleFindErrorTest() {
        Assertions.assertThatThrownBy(() -> articleRepository.findArticleByWriter(100));
    }
}
