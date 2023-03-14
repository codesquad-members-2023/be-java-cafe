package kr.codesqaud.cafe.cafeservice.web.basic;

import kr.codesqaud.cafe.cafeservice.domain.dto.MemberAddDtoRequest;
import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import kr.codesqaud.cafe.cafeservice.domain.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class AddMemberController {

    private final MemberService service;

    @Autowired
    public AddMemberController() {
        service = new MemberService();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user/form.html")
    public String addForm() {
        log.info("f");
        return "user/form";
    }

    @PostMapping("/users")
    public String addMember(@ModelAttribute MemberAddDtoRequest member) {
        service.join(member.toEntity());
        log.info(" memberEmail={}", member.getEmail());
        log.info(" memberpwd={}", member.getPassword());
        log.info(" memberid={}", member.getId());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = service.findMembers();
        model.addAttribute("users", members);
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String findByProfile(@PathVariable Long userId, Model model) {
        Member member = service.findOne(userId);
        model.addAttribute("member", member);
        return "user/profile";
    }
}

