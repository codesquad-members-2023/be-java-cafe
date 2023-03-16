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

    // 회원가입 POST
    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        log.info("회원가입 POST: 보내졌는가?");

        if(repository.save(user)){
            return "redirect:users";
        }
        return "user/form_failed";
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
        Optional<User> profile = repository.findById(userId);

        // 프로필 유무 확인후 성공/실패 넘겨주기
        if(profile.isPresent()) {
            log.info("프로필 Mapping: 프로필 맵핑 성공~~~~~!!!!?");
            model.addAttribute("profile", profile.get());
            return "user/profile";
        }
        log.info("프로필 Mapping: 프로필 맵핑 실패");
        return "user/profile_failed";
    }
}
