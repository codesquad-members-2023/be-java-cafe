package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.MemberRepository;
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

    private final MemberRepository repository;

    @Autowired
    public AddMemberController(MemberRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public String addMember(@ModelAttribute Member member) {
        repository.save(member);
        log.info(" memberEmail={}", member.getEmail());
        log.info(" memberpwd={}", member.getPassword());
        log.info(" memberid={}", member.getId());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = repository.findAll();
        model.addAttribute("users", members);
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String findByProfile(@PathVariable Long userId, Model model) {
        Member member = repository.findOne(userId);
        model.addAttribute("member", member);
        return "user/profile";
    }
}

