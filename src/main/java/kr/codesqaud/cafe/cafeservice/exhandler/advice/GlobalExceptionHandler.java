package kr.codesqaud.cafe.cafeservice.exhandler.advice;

import kr.codesqaud.cafe.cafeservice.controller.MemberController;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.MemberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArticleNotFoundException.class)
    public String articleNotFoundException(ArticleNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "layout/error";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MemberNotFoundException.class)
    public String memberNotFountException(MemberNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "layout/error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return "layout/error";
    }
}
