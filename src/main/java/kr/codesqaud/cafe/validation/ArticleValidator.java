package kr.codesqaud.cafe.validation;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ArticleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Article.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Article article = (Article) target;


    }
}
