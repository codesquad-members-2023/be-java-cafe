package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.exception.LoginFailException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice   // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementFoundException(LoginFailException e) {
        return "user/login_failed";
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementFoundException(NoSuchElementException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "layout/error";
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private String exception(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", "알 수 없는 에러가 발생했습니다.");

        if (e.getMessage()!=null) {
            model.addAttribute("message", e.getMessage());
        }
        return "layout/error";
    }
}
