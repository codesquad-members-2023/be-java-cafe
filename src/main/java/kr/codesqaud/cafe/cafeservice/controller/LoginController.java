package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.dto.LoginDto;
import kr.codesqaud.cafe.cafeservice.service.LoginService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, BindingResult result, HttpSession session) {

        //TODO 로그인 벨리터 만들기
        try {
            Member loginMember = loginService.login(loginDto.getUserId(), loginDto.getPassword());
            session.setAttribute(SessionConst.LOGIN_MEMBER, new LoginSessionUtils(loginMember.getId(), loginMember.getPassword(), loginMember.getNickName()));
        } catch (RuntimeException e) {
            return "user/login_failed";
        }
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
