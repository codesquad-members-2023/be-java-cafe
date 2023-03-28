package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.exception.ManageMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static kr.codesqaud.cafe.dto.SessionUser.SESSION_USER;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String DEFAULT_ERROR_PAGE = "error";

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = InvalidAuthorityException.class)
    public String handleInvalidAuthorityException(InvalidAuthorityException e, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute(MESSAGE, e.getMessage());

        switch (e.getStatus()) {
            case NO_SESSION_USER:
                return "user/login_failed";
            case INVALID_MEMBER:
            default: return DEFAULT_ERROR_PAGE;
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ManageMemberException.class)
    public String handleFailToManageMemberException(ManageMemberException e, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        model.addAttribute(MESSAGE, e.getMessage());

        switch (e.getStatus()) {
            case LOGIN_FAILED:
            case DUPLICATE_MEMBER_INFO:
                return "user/login_failed";
            case UPDATE_FAILED_WRONG_PASSWORD:
                redirectAttributes.addAttribute(MESSAGE, e.getMessage());
                long id = ((SessionUser) httpSession.getAttribute(SESSION_USER)).getId();
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
        model.addAttribute(MESSAGE, "에러가 발생했습니다.");

        if (e.getMessage()!=null) {
            model.addAttribute(MESSAGE, e.getMessage());
        }
        return DEFAULT_ERROR_PAGE;
    }
}
