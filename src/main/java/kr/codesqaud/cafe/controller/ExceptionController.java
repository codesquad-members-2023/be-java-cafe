package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String controlException(Exception ex, Model model) {
        model.addAttribute("ex", ex.getMessage());

        return "error/error-page";
    }
}
