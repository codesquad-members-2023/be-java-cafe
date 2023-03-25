package kr.codesqaud.cafe.validation.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ArticleNewFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Article.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArticleFormDTO article = (ArticleFormDTO) target;
        if (!StringUtils.hasText(article.getTitle())) {
            errors.rejectValue("title", "required.article.title");
        }
        if (!StringUtils.hasText(article.getContents())) {
            errors.rejectValue("contents", "required.article.contents");
        }
    }
}
