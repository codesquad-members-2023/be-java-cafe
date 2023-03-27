package kr.codesqaud.cafe.cafeservice.exhandler.advice;

import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyAdvice {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementFoundException(MemberNotFoundException e) {
        e.printStackTrace();
        return "layout/error";
    }

    @ExceptionHandler(value = Exception.class)
    private String noSuchException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        e.printStackTrace();
        return "layout/error";
    }
}
