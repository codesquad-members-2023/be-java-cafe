package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArticleServiceTest {
    ArticleService articleService = new ArticleService(new MemoryArticleRepository());

    @Test
    @DisplayName("기사가 제대로 저장되어야 함")
    void registerArticle() {
        Article article = new Article("core", "how to code", "안녕하세요. 저는 코어입니다");
        articleService.writeArticle(article);

        Assertions.assertThat(article.getTitle()).isEqualTo(articleService.findOneArticleByTitle("how to code").get().getTitle());
    }
}
