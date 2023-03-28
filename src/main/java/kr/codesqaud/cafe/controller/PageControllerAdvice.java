package kr.codesqaud.cafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PageControllerAdvice {

	@ExceptionHandler(NoLoginException.class)
	public String noLoginException(Exception exception, Model model) {
		model.addAttribute("errorMessage", exception.getMessage());
		return ""
	}
}
