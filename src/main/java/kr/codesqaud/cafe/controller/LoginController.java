package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.login.LoginService;
import kr.codesqaud.cafe.domain.user.Member;
import kr.codesqaud.cafe.dto.MemberLoginDto;
import kr.codesqaud.cafe.repository.NamedJdbcTemplateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private final LoginService loginService;

	private final NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository;

	@Autowired
	public LoginController(LoginService loginService, NamedJdbcTemplateMemberRepository namedJdbcTemplateMemberRepository) {
		this.loginService = loginService;
		this.namedJdbcTemplateMemberRepository = namedJdbcTemplateMemberRepository;
	}

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute MemberLoginDto memberLoginDto, HttpSession session) {

		Member loginMember = loginService.login(memberLoginDto);

		if (loginMember != null) {
			session.setAttribute("sessionedUser", loginMember);
			return "redirect:/";
		}

		return "user/login";
	}
}
