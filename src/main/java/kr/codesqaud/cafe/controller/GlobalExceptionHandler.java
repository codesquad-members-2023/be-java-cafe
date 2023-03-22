package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.utils.UserInfoException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String noneMatchingException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());

        return "exception/error";
    }

    @ExceptionHandler(value = UserInfoException.class)
    public String wrongUserInfoException(UserInfoException e, Model model) {
        model.addAttribute("error", e.getMessage());

        return "user/login_failed";
    }

}
