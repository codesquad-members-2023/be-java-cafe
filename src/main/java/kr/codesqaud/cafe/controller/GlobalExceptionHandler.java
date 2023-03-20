package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice   // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    private String noSuchException(NoSuchElementException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "layout/error";
    }
}
