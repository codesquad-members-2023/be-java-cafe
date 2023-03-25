package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import kr.codesqaud.cafe.dto.article.ArticleUpdateDTO;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.repository.article.H2JDBCArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ArticleTest {

    private final ArticleRepository articleRepository;
    private final int DEFAULT_DB_SIZE = 2;
    private final int WRONG_ID = 10000;

    @Autowired
    public ArticleTest(DataSource dataSource) {
        this.articleRepository = new H2JDBCArticleRepository(dataSource);
    }

    @Test
    @DisplayName("질문글 저장 테스트")
    void articleSaveTest() {
        int articleId = 3;
        ArticleFormDTO article = new ArticleFormDTO("first", "title", "contents");
        articleRepository.save(article);

        Article findArticle = articleRepository.findArticleById(articleId);

        assertThat(findArticle.getId()).isEqualTo(articleId);
    }

    @Test
    @DisplayName("질문글 검색 시 잘못된 id 입력시 예외 발생")
    void articleFindErrorTest() {
        assertThatThrownBy(() -> articleRepository.findArticleById(WRONG_ID));
    }

    @Test
    @DisplayName("전체 질문글 조회 테스트")
    void findAllTest() {
        List<Article> allArticle = articleRepository.findAllArticle();

        assertThat(allArticle.size()).isEqualTo(DEFAULT_DB_SIZE);
    }

    @Test
    @DisplayName("질문글 수정 테스트")
    void articleUpdateTest() {
        int firstArticleId = 1;
        String changeTitle = "change";
        String changeContent = "change";

        ArticleUpdateDTO article = new ArticleUpdateDTO(changeTitle, changeContent);

        articleRepository.updateArticle(article.getTitle(), article.getContents(), firstArticleId);

        Article changedArticle = articleRepository.findArticleById(firstArticleId);

        assertThat(changedArticle.getTitle()).isEqualTo(changeTitle);
        assertThat(changedArticle.getContents()).isEqualTo(changeContent);
    }


    @Test
    @DisplayName("질문글 삭제 테스트")
    void deleteTest() {
        int firstArticleId = 1;
        articleRepository.deleteArticle(firstArticleId);

        assertThatThrownBy(() -> articleRepository.findArticleById(firstArticleId)).isInstanceOf(EmptyResultDataAccessException.class);
    }

}
