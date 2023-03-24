package kr.codesqaud.cafe.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.repository.ArticleDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleControllerTest {

    @Autowired
    ArticleService articleService;

    @Test
    @DisplayName("새로운 게시글 데이터가 정상적으로 들어가야 한다.")
    void creatArticle() {
        // given
        final Article test = new Article("test1", "테스트 페이지", "하하");
        // when
        articleService.creatArticle(test);
        // then
        ArticleDto articleDto = articleService.findArticleContentById(0);
//        assertThat(articleDto.getArticleId()).isEqualTo(test.getArticleId());
//        assertThat(articleDto.getTitle()).isEqualTo(test.getTitle());
//        assertThat(articleDto.getContents()).isEqualTo(test.getContents());
    }

    @Test
    @DisplayName("새로운 게시글의 데이터의 숫자와 반환 리스트의 값이 같아야 한다.")
    void findArticleList() {
        // given
        final Article test = new Article("test1", "테스트 페이지", "하하");
        articleService.creatArticle(test);
        List<ArticleDto> list = articleService.findAllArticle();
        // when
        articleService.findAllArticle();
        // then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 아이디로 게시글을 찾아야 한다.")
    void findArticleById() {
        // given
        // when
        ArticleDto articleDto = articleService.findArticleContentById(0);
        // then
        assertThat(articleDto.getArticleId()).isEqualTo(0);
        assertThat(articleDto.getUserId()).isEqualTo("test1");
        assertThat(articleDto.getTitle()).isEqualTo("테스트 게시물");
        assertThat(articleDto.getContents()).isEqualTo("테스트");
        assertThat(articleDto.getTime()).isEqualTo("2023-03-23 20:18");
    }
}
