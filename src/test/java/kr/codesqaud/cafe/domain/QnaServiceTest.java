package kr.codesqaud.cafe.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import kr.codesqaud.cafe.service.QnaService;
import kr.codesqaud.cafe.service.impl.QnaServiceImpl;
import kr.codesqaud.cafe.model.Article;

class QnaServiceTest {
    QnaService qnaService = new QnaServiceImpl(new MemoryArticleRepository());

    @Test
    @DisplayName("Article을 Repository에 게시한다.")
    void postQna() {
        Article article = new Article("poro", "1", "2");
        qnaService.postQna(article);
        assertThat(qnaService.lookupAllArticles()).contains(article);
    }

    @Test
    @DisplayName("모든 Article의 목록을 반환한다.")
    void lookupAllArticles() {
        Article article1 = new Article("poro", "1", "2");
        Article article2 = new Article("k", "3", "4");
        qnaService.postQna(article1);
        qnaService.postQna(article2);
        assertThat(qnaService.lookupAllArticles()).contains(article1, article2);
    }

    @Test
    @DisplayName("ID에 해당하는 인덱스의 글을 반환한다.")
    void lookupById() {
        Article article1 = new Article("poro", "1", "2");
        Article article2 = new Article("k", "3", "4");
        qnaService.postQna(article1);
        qnaService.postQna(article2);
        assertAll(()-> assertThat(qnaService.lookupById(1)).isEqualTo(article1),
                ()-> assertThat(qnaService.lookupById(2)).isEqualTo(article2));
    }
}
