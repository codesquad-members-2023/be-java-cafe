package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.AddressException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String controlException(Exception ex, Model model) {
        model.addAttribute("ex", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletResponse response, Model model) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("ex", "잘못된 접근입니다.");

        return "error/error";
    }

    @ExceptionHandler(AddressException.class)
    public String handleAddress(HttpServletResponse response, Model model) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("ex", "올바르지 못한 경로입니다.");

        return "error/error";
    }
}
