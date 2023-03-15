package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.CafeConfig;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.repository.MemberRepository;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private MemberService memberService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user")
    public String createUser(UserForm form){
        Member member = new Member(form.getEmail(), form.getUserNickName(), form.getPassword());
        memberService.join(member);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String showList() {
        return "list";
    }
}
