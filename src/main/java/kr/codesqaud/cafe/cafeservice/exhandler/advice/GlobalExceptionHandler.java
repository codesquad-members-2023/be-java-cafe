package kr.codesqaud.cafe.cafeservice.exhandler.advice;

import kr.codesqaud.cafe.cafeservice.exhandler.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String MemberNotFoundException(MemberNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(NullPointerException.class)
    public String MemberNullPointException(NullPointerException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArticleNotFoundException.class)
    public String ArticleNotFoundException(ArticleNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(value = Exception.class)
    private String noSuchException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        e.printStackTrace();
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(Model model, IllegalArgumentException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
}
