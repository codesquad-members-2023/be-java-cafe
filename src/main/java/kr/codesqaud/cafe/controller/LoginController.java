package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.login.LoginService;
import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.MemberLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.Constants.SESSIONED_USER;

@Controller
public class LoginController {

	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute MemberLoginDto memberLoginDto, HttpSession session) {

		Member loginMember = loginService.login(memberLoginDto);

		if (loginMember != null) {
			session.setAttribute(SESSIONED_USER, loginMember.getUserId());
			return "redirect:/";
		}

		return "user/login_failed";
	}
}
