package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private MemberService memberService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/user")
    public String createUser(@RequestParam String email, @RequestParam String nickName, @RequestParam String password){
        memberService.join(new Member(email, nickName, password));
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("lists", memberService.findMembers());
        model.addAttribute("total", memberService.findTotalNumberOfList());
        return "list";
    }

    @GetMapping("profile/{nickName}")
    public String showProfile(@PathVariable String nickName, Model model) {
        model.addAttribute("nickName", nickName);
        String email = memberService.findOneMemberByNickname(nickName).get().getEmail();
        model.addAttribute("email", email);
        return "profile";
    }
}
