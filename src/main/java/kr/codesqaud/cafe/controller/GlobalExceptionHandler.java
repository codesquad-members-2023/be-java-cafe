package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice   // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    private String noSuchException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        e.printStackTrace();
        return "layout/error";
    }
}
