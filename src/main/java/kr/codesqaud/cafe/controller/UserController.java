package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.cglib.reflect.MethodDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private MemberService memberService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user")
    public String createUser(@RequestParam String email, @RequestParam String nickName, @RequestParam String password){
        memberService.join(new Member(email, nickName, password));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("lists", memberService.findMembers());
        System.out.println(memberService.findMembers());
        return "list";
    }
}
