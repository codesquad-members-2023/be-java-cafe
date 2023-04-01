package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.member.MemberRepository;
import kr.codesqaud.cafe.cafeservice.service.MemberService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import kr.codesqaud.cafe.cafeservice.validator.MemberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils.*;

@Controller
@RequestMapping("/users")
public class MemberController {

    private final MemberRepository repository;
    private final MemberService service;
    private final MemberValidator validator;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberRepository repository, MemberService service, MemberValidator validator) {
        this.repository = repository;
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/")
    public String showMember(Model model) {
        List<Member> members = repository.findAll();
        model.addAttribute("users", members);
        model.addAttribute("size", members.size());
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }

    @PostMapping("/create")
    public String addMember(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        System.out.println("member = " + member);
        if (bindingResult.hasErrors()) {
            log.debug("errors = {}", bindingResult);
            return "users/form";
        }
        repository.save(member);
        log.debug("member={}", member);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String memberList(Model model) {
        List<Member> members = repository.findAll();
        log.debug("members{}=", members);
        model.addAttribute("users", members);
        model.addAttribute("size", members.size());
        log.debug("members{}=", members);
        log.debug("model={}", model);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String findByProfile(@PathVariable Long userId, Model model) {
        Member member = service.findById(userId);
        model.addAttribute("profile", member);
        return "user/profile";
    }

    @GetMapping("/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        System.out.println("id = " + id);
        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        System.out.println("sessionUtils = " + sessionUtils);
        sessionCheckId(id, sessionUtils);
        Member member = service.findById(id);
        System.out.println("member = " + member);
        model.addAttribute("user", member);
        return "user/updateForm";
    }


    @PutMapping("/{id}/updateForm")
    public String memberUpdateForm(@Validated @PathVariable Long id, @ModelAttribute Member member,
                                   BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.debug("errors = {}", bindingResult);
            return "redirect:/";
        }

        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        sessionCheckId(id, sessionUtils);
        repository.update(id, member);
        return "redirect:/users";
    }
}

