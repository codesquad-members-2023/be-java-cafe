package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.logout.LogoutService;
import kr.codesqaud.cafe.domain.user.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

	private final LogoutService logoutService;

	@Autowired
	public LogoutController(LogoutService logoutService) {
		this.logoutService = logoutService;
	}

	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		logoutService.logout(httpSession);
		return "redirect:/";
	}
}
