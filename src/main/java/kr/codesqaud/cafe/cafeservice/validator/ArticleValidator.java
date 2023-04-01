package kr.codesqaud.cafe.cafeservice.validator;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.dto.ArticleDTO;
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required", "title is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "content.required", "content is required.");
    }
}
