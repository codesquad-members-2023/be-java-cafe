package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.util.SessionUser;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.exception.ManageMemberException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.net.BindException;
import java.util.NoSuchElementException;

import static kr.codesqaud.cafe.exception.InvalidAuthorityException.INVALID_MEMBER;
import static kr.codesqaud.cafe.exception.InvalidAuthorityException.NO_SESSION_USER;
import static kr.codesqaud.cafe.exception.ManageMemberException.*;
import static kr.codesqaud.cafe.util.SessionUser.SESSION_USER;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String DEFAULT_ERROR_PAGE = "error";
    private static final String LOGIN_FAILED_PAGE = "user/login_failed";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public String handleInvalidAuthorityException(EmptyResultDataAccessException e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return DEFAULT_ERROR_PAGE;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = InvalidAuthorityException.class)
    public String handleInvalidAuthorityException(InvalidAuthorityException e, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute(MESSAGE, e.getMessage());

        switch (e.getMessage()) {
            case NO_SESSION_USER:
                return LOGIN_FAILED_PAGE;
            case INVALID_MEMBER:
            default: return DEFAULT_ERROR_PAGE;
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ManageMemberException.class)
    public String handleFailToManageMemberException(ManageMemberException e, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        switch (e.getMessage()) {
            case LOGIN_FAILED:
            case DUPLICATE_MEMBER_INFO:
                model.addAttribute(MESSAGE, e.getMessage());
                return LOGIN_FAILED_PAGE;
            case UPDATE_FAILED_WRONG_PASSWORD:
                long id = ((SessionUser) httpSession.getAttribute(SESSION_USER)).getId();
                redirectAttributes.addAttribute(MESSAGE, e.getMessage());
                redirectAttributes.addFlashAttribute(id);
                return "redirect:/users/{id}/update";
            default: return DEFAULT_ERROR_PAGE;
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementFoundException(NoSuchElementException e, Model model) {
        model.addAttribute(MESSAGE, e.getMessage());
        return DEFAULT_ERROR_PAGE;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    private String exception(Exception e, Model model) {
        e.printStackTrace();

        if (e.getMessage()!=null) {
            model.addAttribute(MESSAGE, e.getMessage());
        }

        return DEFAULT_ERROR_PAGE;
    }
}
