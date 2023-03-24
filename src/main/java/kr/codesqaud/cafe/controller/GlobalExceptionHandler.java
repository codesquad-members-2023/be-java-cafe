package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.ManageMemberException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@ControllerAdvice   // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ManageMemberException.class)
    public String handleFailToManageMemberException(ManageMemberException e, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        model.addAttribute("message", e.getMessage());

        switch (e.getStatus()) {
            case NO_SESSION_USER:
            case LOGIN_FAIL:
            case DUPLICATE_MEMBER_INFO:
                return "user/login_failed";
            case WRONG_PASSWORD:
                redirectAttributes.addAttribute("message", e.getMessage());
                String id = ((Member) httpSession.getAttribute("sessionedUser")).getUserId();
                redirectAttributes.addFlashAttribute(id);
                return "redirect:/user/{id}/update";
            default: return "error";
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementFoundException(NoSuchElementException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String exception(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("message", "에러가 발생했습니다.");

        if (e.getMessage()!=null) {
            model.addAttribute("message", e.getMessage());
        }
        return "error";
    }
}
