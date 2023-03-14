package kr.codesqaud.cafe.cafeservice.web.basic;

import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import kr.codesqaud.cafe.cafeservice.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class AddMemberController {

    @Autowired
    private final MemberRepository memberRepository;

    public AddMemberController() {
        memberRepository = new MemberRepository();
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
    public String addMember(@ModelAttribute Member member) {
        memberRepository.save(member);
        log.info(" member={}", member);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("users", members);
        log.info("members={}", members);
        log.info("model={}", model);
        return "user/list";
    }
}

