package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.repository.article.H2JDBCArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class ArticleSaveTest {

    private final ArticleRepository articleRepository;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleSaveTest(DataSource dataSource) {
        this.articleRepository = new H2JDBCArticleRepository(dataSource);
    }

    @Test
    @DisplayName("질문글 저장 테스트")
    void articleSaveTest() {
        Article article = new Article("writer", "title", "contents", Timestamp.valueOf(LocalDateTime.now()));
        articleRepository.save(article);


        Article findArticle = articleRepository.findArticleById(3);

        Assertions.assertThat(findArticle.getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("잘못된 id 입력시 예외 발생")
    void articleFindErrorTest() {
        Assertions.assertThatThrownBy(() -> articleRepository.findArticleById(100));
    }
}
