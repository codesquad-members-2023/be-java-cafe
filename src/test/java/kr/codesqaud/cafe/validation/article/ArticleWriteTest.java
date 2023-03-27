package kr.codesqaud.cafe.validation.article;

import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ArticleWriteTest {

    private final ArticleNewFormValidator articleValidator;
    private ArticleErrorConstant articleError = new ArticleErrorConstant();
    private ArticleInformationConstant articleInformation = new ArticleInformationConstant();

    @Autowired
    public ArticleWriteTest(ArticleNewFormValidator articleValidator) {
        this.articleValidator = articleValidator;
    }

    @Test
    @DisplayName("새로운 글 작성 시, 제목이 비어있으면 에러 발생")
    void checkNoTitle() {
        ArticleFormDTO article = new ArticleFormDTO(articleInformation.USERID, articleInformation.BLANK, articleInformation.CONTENTS);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(articleError.TITLE_ERROR_CODE);
    }

    @Test
    @DisplayName("새로운 글 작성 시, 내용이 비어있으면 에러 발생")
    void checkNoContents() {
        ArticleFormDTO article = new ArticleFormDTO(articleInformation.USERID, articleInformation.TITLE, articleInformation.BLANK);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(articleError.CONTENT_ERROR_CODE);
    }

    @Test
    @DisplayName("형식에 맞는 글 작성 시 에러 없이 정상 등록")
    void writeArticle() {
        ArticleFormDTO article = new ArticleFormDTO(articleInformation.USERID, articleInformation.TITLE, articleInformation.CONTENTS);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("업데이트 제목 길이 제한")
    void limitUpdateTitleLength() {
        ArticleFormDTO article = new ArticleFormDTO(articleInformation.USERID, articleInformation.TITLE_LENGTH_OVER, articleInformation.CONTENTS);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(articleError.TITLE_LENGTH_ERROR);
    }

    @Test
    @DisplayName("업데이트 본문 길이 제한")
    void limitUpdateContentsLength() {
        ArticleFormDTO article = new ArticleFormDTO(articleInformation.USERID, articleInformation.TITLE, articleInformation.CONTENTS_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(article, "article");
        articleValidator.validate(article, errors);
        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(articleError.CONTENTS_LENGTH_ERROR);
    }

}
