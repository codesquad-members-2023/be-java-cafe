package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.exception.userException.UserException;
import kr.codesqaud.cafe.exception.userException.UserLoginException;
import kr.codesqaud.cafe.exception.userException.UserSessionExpireException;
import kr.codesqaud.cafe.exception.userException.UserUpdateException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler({UserException.class})
    public String userException(Exception e,
                                Model model) {
        model.addAttribute("exception", e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(UserSessionExpireException.class)
    public String userSessionExpireException(Exception e,
                                Model model) {
        model.addAttribute("exception", e.getMessage());
        return "user/loginForm";
    }

    @ExceptionHandler(UserLoginException.class)
    public String userLoginException(Exception e,
                                     Model model) {
        model.addAttribute("exception", e.getMessage());
        return "user/loginForm";
    }


    // TODO : url 맵핑 및 html 수정
    @ExceptionHandler(UserUpdateException.class)
    public String userWrongPasswordException(Exception e,
                                             Model model) {
        model.addAttribute("exception", e.getMessage());
        return "errorPage";
    }

}
