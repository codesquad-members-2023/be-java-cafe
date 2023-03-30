package kr.codesqaud.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public String badRequest() {
        return "error/400";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({HttpClientErrorException.NotFound.class})
    public String notFound(){
        return "error/404";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({NoSuchElementException.class})
    public String noValuePresent(){
        return "error/500";
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public String MethodNotSupport(){
        return "error/405";
    }

}
