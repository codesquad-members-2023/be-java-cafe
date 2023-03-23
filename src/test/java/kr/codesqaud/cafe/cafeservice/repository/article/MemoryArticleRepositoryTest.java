package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemoryArticleRepositoryTest {

    ArticleRepository repository;

    public MemoryArticleRepositoryTest(@Qualifier ArticleRepository articleRepository) {
        this.repository = articleRepository;
    }

    @Test
    @DisplayName("Article 저장")
    void save() {
        Article article = new Article("감자", "제목", "내용");
        repository.save(article);
        assertEquals(article.getId(), 0);
    }

    @Test
    @DisplayName("Article 아이디 탐색")
    void findById() {
        Article article = new Article("감자", "제목", "내용");
        repository.save(article);
        Optional<Article> byId = repository.findById(0L);
        assertEquals(article, byId.orElseThrow());
    }

    @Test
    @DisplayName("Article 모두 출력")
    void all() {
        List<Article> all = repository.findAll();
        for (Article article : all) {
            System.out.println(article);
        }
        System.out.println();
    }
}
