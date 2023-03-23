package kr.codesqaud.cafe.cafeservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdvice {
    @ExceptionHandler(value = Exception.class)
    private String noSuchException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        e.printStackTrace();
        return "layout/error";
    }
}
