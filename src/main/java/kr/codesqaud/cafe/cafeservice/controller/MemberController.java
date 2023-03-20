package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberRepository repository;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public String addMember(@ModelAttribute Member member) {
        log.info(" member", member);
        repository.save(member);
        log.info(" memberID={}", member.getId());
        log.info(" memberEmail={}", member.getEmail());
        log.info(" memberName={}", member.getUserName());
        log.info(" memberPwd={}", member.getPassword());
        log.info(" memberCerateDate={}", member.getCreatedDate());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String memberList(Model model) {
        List<Member> members = repository.findAll();
        model.addAttribute("users", members);
        model.addAttribute("size", members.size());
        log.info(" member={}", members.get(0).getId());
        log.info("model={}", model);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String findByProfile(@PathVariable Long userId, Model model) {
        try {
            Optional<Member> byId = repository.findById(userId);
            model.addAttribute("member", byId.orElseThrow());
            return "user/profile";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        }
    }

    @GetMapping("/users/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        try {
            Optional<Member> byId = repository.findById(id);
            model.addAttribute("user", byId.orElseThrow());
            log.info("1111model={}",model);
            log.info("111111id={}",id);
            return "user/updateForm";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        }
    }

    @PutMapping("/users/{id}/updateForm")
    public String memberUpdateForm(@PathVariable Long id, @ModelAttribute Member member) {
        try {
            log.info("22222222member={}",member);
            log.info("22222222id={}",id);
            repository.update(id, member);
            return "redirect:/users";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        }
    }
}

