package kr.codesqaud.cafe.cafeservice.validator;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Article.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.rejectValue("article", "required.article");
            return;
        }
        Article article = (Article) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "article", "required");
        if (article.getTitle() == null) {
            errors.rejectValue("title", "required.title");
        }
        if (article.getContent() == null) {
            errors.rejectValue("content", "required.content");
        }
    }
}
