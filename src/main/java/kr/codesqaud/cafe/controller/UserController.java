package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/user")  // 기본 경로를 설정해줌
public class UserController {
    private MemberService memberService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/list")
    public String createUser(@RequestParam String email, @RequestParam String nickName, @RequestParam String password){
        memberService.join(new Member(email, nickName, password));
        return "redirect:user/list";
    }

    @GetMapping("user/list")
    public String showList(Model model) {
        model.addAttribute("lists", memberService.findMembers());
        model.addAttribute("total", memberService.findTotalNumberOfList());
        return "user/list";
    }

    @GetMapping("profile/{nickName}")
    public String showProfile(@PathVariable String nickName, Model model) {
        model.addAttribute("nickName", nickName);
        String email = memberService.findOneMemberByNickname(nickName).get().getEmail();
        model.addAttribute("email", email);
        return "user/profile";
    }


}
