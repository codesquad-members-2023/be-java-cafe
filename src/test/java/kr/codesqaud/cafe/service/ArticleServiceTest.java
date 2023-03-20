package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArticleServiceTest {
    MemoryArticleRepository articleRepository = new MemoryArticleRepository();
    ArticleService articleService = new ArticleService(articleRepository);


    @AfterEach
    void clearArticleRepository() {
        articleRepository.clearRepository();
    }

    @Test
    @DisplayName("기사가 제대로 저장되어야 함")
    void registerArticle() {
        Article article = new Article("core", "how to code", "안녕하세요. 저는 코어입니다");
        articleService.writeArticle(article);

//        Assertions.assertThat(article.getId()).isEqualTo(articleService.findOneArticleById(0).get().getTitle());
    }

    @Test
    @DisplayName("올바른 기사 id값을 가지는지 확인")
    void checkRightId() {
        Article article = new Article("cre", "안녕하세요", "룰루랄라");
        articleService.writeArticle(article);
        Assertions.assertThat(article.getId()).isEqualTo(1);
    }
}
