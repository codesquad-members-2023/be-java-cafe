package kr.codesqaud.cafe.validation.article;

import kr.codesqaud.cafe.dto.article.ArticleUpdateDTO;
import kr.codesqaud.cafe.validation.article.ArticleNewFormValidator;
import kr.codesqaud.cafe.validation.article.ArticleUpdateValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ArticleUpdateTest {


    private final ArticleUpdateValidator articleValidator;
    private TestArticleConstant testArticleConstant = new TestArticleConstant();

    @Autowired
    public ArticleUpdateTest(ArticleUpdateValidator articleValidator) {
        this.articleValidator = articleValidator;
    }

    @Test
    @DisplayName("업데이트 시, 제목이 비어있으면 에러 발생")
    void checkNoTitle() {
        ArticleUpdateDTO article = new ArticleUpdateDTO("", "content");
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testArticleConstant.TITLE_ERROR_CODE);
    }

    @Test
    @DisplayName("업데이트 시, 내용이 비어있으면 에러 발생")
    void checkNoContents() {
        ArticleUpdateDTO article = new ArticleUpdateDTO("title", "");
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testArticleConstant.CONTENT_ERROR_CODE);
    }

    @Test
    @DisplayName("형식에 맞는 업데이트 작성 시 에러 없이 정상 등록")
    void writeArticle() {
        ArticleUpdateDTO article = new ArticleUpdateDTO("title", "contents");
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("업데이트 제목 길이 제한")
    void limitUpdateTitleLength() {
        ArticleUpdateDTO article = new ArticleUpdateDTO(testArticleConstant.TITLE_LENGTH_OVER, "contents");
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testArticleConstant.TITLE_LENGTH_ERROR);
    }

    @Test
    @DisplayName("업데이트 본문 길이 제한")
    void limitUpdateContentsLength() {
        ArticleUpdateDTO article = new ArticleUpdateDTO("title", testArticleConstant.CONTENTS_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testArticleConstant.CONTENTS_LENGTH_ERROR);
    }
}
