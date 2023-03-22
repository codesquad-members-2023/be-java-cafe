package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String login(String userId, String password, HttpSession session, Model model) {
        User loginUser = repository.findByUserId(userId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
        if (loginUser == null) {

            log.debug("로그인 실패! ㅠㅠ");
            model.addAttribute("loginFailCheck", true);
            return "user/login";
        }

        // 로그인 성공 처리
        log.debug("로그인 성공!!!");

        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        //HttpSession session1 = requset.getSession(); <- 디폴트가 true 라서 안써도됨
        // 세션에 로그인 회원 정보 보관
        session.setAttribute("loginUser",loginUser);

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
