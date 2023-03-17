package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private Logger LOG = LoggerFactory.getLogger(UserController.class.getName());

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String addUser(String userId, String email, String nickname, String password) {
        userRepository.save(new Member(userId, email, nickname, password));
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<Member> all = userRepository.findAll();

        model.addAttribute("list", all);
        model.addAttribute("size", all.size());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        Optional<Member> user = userRepository.findById(userId);
        try {
            model.addAttribute("profile", user.orElseThrow(() -> new NoSuchElementException("해당하는 회원이 없습니다.")));
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "layout/error";
        }
        return "user/profile";
    }

    @GetMapping("/user/{userId}/updateForm")
    public String updateProfileForm(@PathVariable Long userId, Model model) {
        try {
            Member byId = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("[exception] 정보를 수정할 회원이 없습니다."));
            model.addAttribute("profile", byId);
            LOG.info("test={}", byId.getNickname());
            return "user/updateForm";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "layout/error";
        }
    }

    @PutMapping ("/users/{id}/update")
    public String updateProfile(@ModelAttribute Member member,
                                @PathVariable Long id,
                                @RequestParam String exPassword,
                                Model model) {
        try {
            Member exMember = userRepository.findById(id).orElseThrow();
            if (!exMember.isValidPassword(exPassword)) {
                throw new IllegalAccessException("[exception] 비밀번호가 다릅니다.");
            }
            userRepository.update(exMember, member);
        } catch (NoSuchElementException | IllegalAccessException e) {    // e만쓰지 말고 다 적어주는 것이 올바름.
            model.addAttribute("message", e.getMessage());
            return "layout/error";
        }
        return "redirect:/users";
    }

}
