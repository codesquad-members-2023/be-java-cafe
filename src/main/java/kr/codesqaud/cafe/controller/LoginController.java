package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.member.JdbcMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private JdbcMemberRepository jdbcMemberRepository;

    public LoginController(JdbcMemberRepository jdbcMemberRepository) {
        this.jdbcMemberRepository = jdbcMemberRepository;
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping("/login")
    public String login() throws Exception {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") Member member, HttpServletRequest request,Model model) {
        logger.debug("login {} ", member);
        Member existUser = jdbcMemberRepository.findById(member.getUserId())
                .filter(m -> m.getPassword().equals(member.getPassword()))
                .orElse(null);

        if (existUser == null) {
            model.addAttribute("notExist", "fail");
            return "user/login";
        }
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 반환
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute(MemberSessionUser.LOGIN_MEMBER, existUser);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "user/login";
    }
}
