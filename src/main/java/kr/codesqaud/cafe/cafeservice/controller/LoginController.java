package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.domain.login.LoginService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        Member loginMember = loginService.login(userId, password);
        if (loginMember == null) {
            throw new NullPointerException();
        }

        session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginSessionUtils(loginMember.getId(), loginMember.getPassword()));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
