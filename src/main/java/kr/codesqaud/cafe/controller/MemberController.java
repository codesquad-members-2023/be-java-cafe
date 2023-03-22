package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private Logger LOG = LoggerFactory.getLogger(MemberController.class.getName());

    public MemberController(MemberRepository userRepository) {
        this.memberRepository = userRepository;
    }

    @GetMapping("/user/{id}/update")
    public String updateForm(@PathVariable Long id, HttpSession httpSession, Model model) {
        Object sessionedUser = httpSession.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            throw new IllegalArgumentException("먼저 로그인을 해주세요.");
        }

        Member member = (Member) sessionedUser;

        if (!Objects.equals(member.getId(), id)) {
            throw new NoSuchElementException("로그인한 유저가 아닙니다.");
        }

        model.addAttribute("profile", memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("정보를 수정할 회원이 없습니다.")));
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(String userId, String password, HttpSession httpSession) {
        Member member = memberRepository.findByMemberId(userId).orElseThrow(() -> new NoSuchElementException("해당하는 유저가 없습니다."));

        if (!member.getPassword().equals(password)) {
            return "user/login_failed";
        }

        httpSession.setAttribute("sessionedUser", member);
        return "redirect:/";
    }

    @PostMapping("/users")
    public String addUser(String userId, String email, String nickname, String password) {
        memberRepository.save(new Member(userId, nickname, email, password));
        return "redirect:/users";
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
        Optional<Member> user = memberRepository.findById(userId);
        model.addAttribute("profile", user.orElseThrow(() -> new NoSuchElementException("해당하는 회원이 없습니다.")));
        return "user/profile";
    }

    @PutMapping ("/users/{id}/update")
    public String updateProfile(@ModelAttribute Member member,
                                @PathVariable Long id,
                                @RequestParam String exPassword) {
        Member exMember = memberRepository.findById(id).orElseThrow();
        if (!exMember.isValidPassword(exPassword)) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
        memberRepository.update(exMember, member);
        return "redirect:/users";
    }

}
