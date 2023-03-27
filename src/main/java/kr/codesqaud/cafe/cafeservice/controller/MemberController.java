package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.exhandler.exception.ArticleNotFoundException;
import kr.codesqaud.cafe.cafeservice.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        try {
            Optional<Member> byId = repository.findById(userId);
            model.addAttribute("member", byId.orElseThrow());

            if (byId == null) {
                throw new ArticleNotFoundException("Article not found with id " + byId);
            }

            return "user/profile";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        }
    }

    @GetMapping("/users/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Object value = session.getAttribute("loginMember");
            if (value != null) {
                Member member = (Member) value;
                Optional<Member> byId = repository.findById(member.getId());
                model.addAttribute("user", byId.orElseThrow());
                return "user/updateForm";
            }
            return "fail";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        }
    }

    @PutMapping("/users/{id}/updateForm")
    public String memberUpdateForm(@PathVariable Long id, @ModelAttribute Member member) {
        try {
            repository.update(id, member);
            return "redirect:/users";
        } catch (NoSuchElementException e) {
            log.debug("예외발생");
            return "fail";
        } catch (Exception e) {
            log.error("Exception 예외발생", e);
            return "fail";
        }
    }
}

