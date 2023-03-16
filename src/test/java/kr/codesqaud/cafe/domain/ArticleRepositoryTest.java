package kr.codesqaud.cafe.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.codesqaud.cafe.user.Article;

class ArticleRepositoryTest {
    private ArticleRepository articleRepository = new MemoryArticleRepository();

    private static final Article art1 = new Article("poro", "글쓰기는 쉽다.", "알고보면 글쓰기는 어려울지도");
    private static final Article art2 = new Article("honux", "코딩은 쉽다.", "쉽다");
    @BeforeEach
    void setup() {
        //given
        articleRepository.addArticle(art1);
        articleRepository.addArticle(art2);
    }

    @Test
    @DisplayName("글 타입을 인자로 받아 Repository에 저장한다.")
    void addArticle() {
        //then
        assertAll(()->assertThat(art1.getWriter()).isEqualTo("poro"),
                ()->assertThat(art1.getTitle()).isEqualTo("글쓰기는 쉽다."),
                ()->assertThat(art1.getContents()).isEqualTo("알고보면 글쓰기는 어려울지도"));
    }

    @Test
    @DisplayName("글 목록을 반환한다.")
    void getArticleList() {
        //when
        assertAll(()-> assertThat(articleRepository.getArticleList().contains(art1)),
                ()-> assertThat(articleRepository.getArticleList()).contains(art2));
    }

    @Test
    @DisplayName("ID에 해당하는 인덱스의 글을 반환한다.")
    void findById() {
        //when
        assertAll(()-> assertThat(articleRepository.findById(1).get()).isEqualTo(art1),
                ()-> assertThat(articleRepository.findById(2).get()).isEqualTo(art2));
    }
}
