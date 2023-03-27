package kr.codesqaud.cafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionController {

    @ExceptionHandler(IllegalArgumentException.class)
    public String controlException(Exception ex, Model model) {
        model.addAttribute("ex", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, HttpServletResponse response, Exception ex, Model model) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("ex", "뒤로가기를 할 수 없습니다.");
        return "error/error";
    }
}
