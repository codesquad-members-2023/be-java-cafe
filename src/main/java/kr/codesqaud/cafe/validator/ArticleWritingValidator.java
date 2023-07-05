package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.domain.dto.ArticleForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ArticleWritingValidator implements Validator {

    private static int MAX_TITLE_LENGTH = 70;
    private static int MAX_CONTENTS_LENGTH = 1000;

    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArticleForm articleForm = (ArticleForm) target;

        if (articleForm.getTitle().equals("")) {
            errors.rejectValue("title", "empty.articleForm.title", "제목을 입력해주세요.");
        }

        if (articleForm.getContents().equals("")) {
            errors.rejectValue("contents", "empty.articleForm.contents", "내용을 입력해주세요.");
        }

        if (articleForm.getTitle().length() >= MAX_TITLE_LENGTH) {
            errors.rejectValue("title", "over.articleForm.title", "제목은 최대 70자까지 작성할 수 있습니다.");
        }

        if (articleForm.getContents().length() >= MAX_CONTENTS_LENGTH) {
            errors.rejectValue("contents", "over.articleForm.contents", "게시글은 최대 1000자까지 작성할 수 있습니다.");
        }
    }
}
