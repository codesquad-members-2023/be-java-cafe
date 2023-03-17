package kr.codesqaud.cafe.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public String noneMatchingException(IllegalArgumentException e) {
        //수정 필요
        return "에러가 발생했습니다.";
    }
}
