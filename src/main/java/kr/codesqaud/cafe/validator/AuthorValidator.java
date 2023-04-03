package kr.codesqaud.cafe.validator;

import org.springframework.stereotype.Controller;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.exceptions.UserInfoException;

@Controller
public class AuthorValidator {
    public static void validateAuthorization(boolean isValid, String errorMessage,
            int errorCode) throws ArticleInfoException {
        if (!isValid) {
            throw new ArticleInfoException(errorMessage, errorCode);
        }
    }

    public static void validateUser(boolean isValid, String errorMessage,
            int errorCode) throws UserInfoException {
        if (!isValid) {
            throw new UserInfoException(errorMessage, errorCode);
        }
    }
}
