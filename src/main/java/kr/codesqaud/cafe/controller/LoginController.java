package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public String login(Member member, HttpSession httpSession) {
        Member loginMember = memberService.findOneMemberByEmail(member.getEmail()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if (loginMember != null && memberService.checkMember(loginMember, member)) {
            httpSession.setAttribute("sessionedUser", loginMember);
            return "redirect:/";
        }
        return "redirect:/login_fail";
    }
}
