package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import kr.codesqaud.cafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private MemberService memberService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/join")
    public String createUser(Member member){
        memberService.join(member);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model) {
        model.addAttribute("lists", memberService.findMembers());
        model.addAttribute("total", memberService.findTotalNumberOfList());
        return "user/list";
    }

    @GetMapping("/profile/{nickName}")
    public String showProfile(@PathVariable String nickName, Model model) {
        model.addAttribute("nickName", nickName);
        String email = memberService.findOneMemberByNickname(nickName).getEmail();
        model.addAttribute("email", email);
        return "user/profile";
    }

    @GetMapping("/users/{emailurl}/updateForm")
    public String showUpdateForm(@PathVariable String emailurl, Model model) {
//        String email = memberService.findOneMemberByEmail(emailurl).getEmail();
        model.addAttribute("email", emailurl);
        return "user/updateForm";
    }

    @PutMapping("/user/{emailurl}/updateUser")
    public String updateUser(@PathVariable String emailurl, @ModelAttribute Member member) {
        memberService.editeMember(member, emailurl);
        return "redirect:/user/list";
    }
}
