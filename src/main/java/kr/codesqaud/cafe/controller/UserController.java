package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
