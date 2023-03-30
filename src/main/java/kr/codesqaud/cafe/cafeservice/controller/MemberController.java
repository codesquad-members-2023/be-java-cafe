package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.member.MemberRepository;
import kr.codesqaud.cafe.cafeservice.service.MemberService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils.*;

@Controller
public class MemberController {

    private final MemberRepository repository;
    private final MemberService service;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberRepository repository, MemberService service) {
        this.repository = repository;
        this.service = service;
    }


    @PostMapping("/users")
    public String addMember(@ModelAttribute Member member) {
        log.info(" member", member);
        repository.save(member);
        log.debug("member={}", member);
        return "redirect:/users";
    }

    @GetMapping("/users/list")
    public String memberList(Model model) {
        List<Member> members = repository.findAll();
        log.debug("members{}=", members);
        model.addAttribute("users", members);
        model.addAttribute("size", members.size());
        log.debug("members{}=", members);
        log.debug("model={}", model);
        return "user/list";
    }

    @GetMapping("/users")
    public String showMember(Model model) {
        List<Member> members = repository.findAll();
        model.addAttribute("users", members);
        model.addAttribute("size", members.size());
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String findByProfile(@PathVariable Long userId, Model model) {
        Member member = service.findById(userId);
        model.addAttribute("profile", member);
        return "user/profile";
    }

    @GetMapping("/users/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        Member member = service.findById(id);
        model.addAttribute("user", member);
        return "user/updateForm";
    }


    @PutMapping("/users/{id}/updateForm")
    public String memberUpdateForm(@PathVariable Long id, @ModelAttribute Member member, HttpSession session) {
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        repository.update(id, member);
        return "redirect:/users";
    }
}

