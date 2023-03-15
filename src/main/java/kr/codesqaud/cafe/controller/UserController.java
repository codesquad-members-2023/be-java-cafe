package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final MemoryUserRepository repository;

    @Autowired
    public UserController(MemoryUserRepository repository) {
        this.repository = repository;
    }

    // form.html Mapping
    @GetMapping("/user/form.html")
    public String showForm() {
        log.info("이것은 Form 인가?!");

        return "user/form";
    }

    // 회원가입 POST
    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        log.info("회원가입 POST: 보내졌는가?");

        repository.save(user);
        return "redirect:users";
    }

    // 사용자 목록 Mapping
    @GetMapping("/users")
    public String showUsers(Model model) {
        log.info("사용자 목록 Mapping: 받았는가?");

        List<User> users = repository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    // 프로필 Mapping
    @GetMapping("/users/{userId}")
    public String showUserProfile(Model model, @PathVariable String userId) {
        log.info("프로필 Mapping: 프로필 보러왔는감?");

        Optional<User> profile = repository.findById(userId);
        model.addAttribute("profile", profile.orElse(null));
        return "user/profile";
    }
}
