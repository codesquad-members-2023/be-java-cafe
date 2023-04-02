package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.JoinRequest;
import kr.codesqaud.cafe.util.SessionUser;
import kr.codesqaud.cafe.exception.*;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_INDEX;
import static kr.codesqaud.cafe.exception.InvalidAuthorityException.INVALID_MEMBER;
import static kr.codesqaud.cafe.exception.ManageMemberException.*;
import static kr.codesqaud.cafe.util.SessionUser.SESSION_USER;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private Logger LOG = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberRepository userRepository) {
        this.memberRepository = userRepository;
    }

    @GetMapping("/users/{id}/update")
    public String updateForm(@PathVariable long id, HttpSession httpSession, Model model) {
        model.addAttribute("profile", memberRepository.findById(id));
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return REDIRECT_INDEX;
    }

    @PostMapping("/login")
    public String loginUser(String userId, String password, HttpSession httpSession) {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(() -> new ManageMemberException(LOGIN_FAILED));

        if (!member.isValidPassword(password)) {
            throw new ManageMemberException(LOGIN_FAILED);
        }

        httpSession.setAttribute(SESSION_USER, new SessionUser(member.getId(), member.getNickname()));
        return REDIRECT_INDEX;
    }

    @PostMapping("/users/join")
    public String addUser(@Valid JoinRequest joinForm) {
        Member member = joinForm.toEntity();

        if (!memberRepository.validMemberId(member.getUserId())) {
            throw new ManageMemberException(DUPLICATE_MEMBER_INFO);
        }

        memberRepository.save(member);
        return REDIRECT_INDEX;
    }

    @GetMapping("/users")
    public String list(HttpSession httpSession, Model model) {
        List<Member> all = memberRepository.findAll();

        model.addAttribute("list", all);
        model.addAttribute("size", all.size());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        model.addAttribute("profile", memberRepository.findById(userId));
        return "user/profile";
    }

    @PutMapping ("/users/{id}/update")
    public String updateProfile(@ModelAttribute Member member,
                                @PathVariable Long id,
                                @RequestParam String exPassword,
                                HttpSession httpSession) throws InvalidAuthorityException {
        Member exMember = memberRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if(!sessionUser.equals(id)) {
            throw new InvalidAuthorityException(INVALID_MEMBER);
        }

        if (!exMember.isValidPassword(exPassword)) {
            throw new ManageMemberException(UPDATE_FAILED_WRONG_PASSWORD);
        }

        memberRepository.update(exMember, member);
        return "redirect:/users";
    }

}
