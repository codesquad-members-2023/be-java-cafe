package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.Member.JdbcMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String login(@ModelAttribute("user") Member member, HttpSession session, Model model) {
        logger.debug("login {} ", member);
        Member existUser = jdbcMemberRepository.findById(member.getUserId())
                .filter(m -> m.getPassword().equals(member.getPassword()))
                .orElse(null);

        if (existUser == null) {
            model.addAttribute("notExist", "fail");
            return "user/login";
        }
        session.setAttribute("sessionedUser", existUser);
        return "redirect:/";
    }
}
