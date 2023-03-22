package kr.codesqaud.cafe.controller;

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


}
