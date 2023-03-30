package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.service.SessionUtil.setUserInfo;

@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User loginUser = userService.passwordCheck(userId, password);
        if (loginUser == null) {
            log.debug("로그인 실패! ㅠㅠ");
            return "user/login_failed";
        }

        log.debug("로그인 성공!!!");
        // 세션에 로그인 회원 정보 보관
        setUserInfo(session, loginUser);

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
