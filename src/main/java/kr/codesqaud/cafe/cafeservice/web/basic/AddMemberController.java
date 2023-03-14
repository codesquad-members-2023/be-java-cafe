package kr.codesqaud.cafe.cafeservice.web.basic;

import kr.codesqaud.cafe.cafeservice.domain.dto.MemberAddDtoRequest;
import kr.codesqaud.cafe.cafeservice.domain.member.Member;
import kr.codesqaud.cafe.cafeservice.domain.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    void init() {
        service.save(new Member(1L, "1234", "황현", "ghkdgus29@naver.com"));
        service.save(new Member(2L, "4321", "황윤", "ghkddbs28@naver.com"));
    }

    @PostMapping("/users")
    public String addMember(@ModelAttribute MemberAddDtoRequest member) {
        service.save(member.toEntity());
        log.info(" memberId={}", member.getEmail());
        log.info(" memberId={}", member.getPassword());
        log.info(" memberId={}", member.getPassword());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = service.findAll();
        model.addAttribute("users", members);
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }
}

