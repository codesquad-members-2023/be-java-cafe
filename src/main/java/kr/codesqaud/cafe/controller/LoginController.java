package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String SESSION_ID = "sessionedUser";

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        User loginUser;

        try {
            loginUser = loginService.login(userId, password);
        } catch (IllegalArgumentException e) {
            log.error("로그인중 에러 발생", e);
            return "redirect:/user/login.html";
        }

        session.setAttribute(SESSION_ID, loginUser);
        return "redirect:/";
    }
}
