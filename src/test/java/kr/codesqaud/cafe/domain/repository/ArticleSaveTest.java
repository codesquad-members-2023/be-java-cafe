package kr.codesqaud.cafe.domain.repository;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.ArticleService;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ArticleSaveTest {

    private ArticleService articleService = new ArticleService(new MemoryArticleRepository());

    @Test
    @DisplayName("질문글 저장 테스트")
    void articleSaveTest() {
        Article article = new Article("writer", "title", "contents");
        articleService.writeArticle(article);

        Article findArticle = articleService.findArticleById(article.getId()).get();

        Assertions.assertThat(findArticle).isEqualTo(article);
    }
}
