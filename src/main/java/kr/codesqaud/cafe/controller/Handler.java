package kr.codesqaud.cafe.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String noneMatchingException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());

        return "exception/error";
    }


}
