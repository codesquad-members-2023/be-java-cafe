package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice   // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    private String exception(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", "알 수 없는 에러가 발생했습니다.");

        if (e.getMessage()!=null) {
            model.addAttribute("message", e.getMessage());
        }
        return "layout/error";
    }
}
