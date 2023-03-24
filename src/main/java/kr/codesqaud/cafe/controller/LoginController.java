package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final JdbcTemplateUserRepository repository;

    @Autowired
    public LoginController(JdbcTemplateUserRepository repository) {
        this.repository = repository;
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User loginUser = repository.findByUserId(userId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
        if (loginUser == null) {
            log.debug("로그인 실패! ㅠㅠ");
            return "user/login_failed";
        }
        // 로그인 성공 처리
        log.debug("로그인 성공!!!");
        // 세션에 로그인 회원 정보 보관
        session.setAttribute("loginUser", loginUser);

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }
}
