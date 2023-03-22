package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final JdbcTemplateUserRepository repository;

    @Autowired
    public UserController(JdbcTemplateUserRepository repository) {
        this.repository = repository;
    }

    // 회원가입 POST
    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        log.debug("회원가입 POST: 보내졌는가?");

        if (repository.save(user)) {
            return "redirect:users";
        }
        return "user/form_failed";
    }

    // 사용자 목록 Mapping
    @GetMapping("/users")
    public String showUsers(Model model) {
        log.debug("사용자 목록 Mapping: 받았는가?");

        List<User> users = repository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    // 프로필 Mapping
    @GetMapping("/users/{userId}")
    public String showUserProfile(Model model, @PathVariable String userId) {
        Optional<User> profile = repository.findByUserId(userId);

        // 프로필 유무 확인후 성공/실패 넘겨주기
        if (profile.isPresent()) {
            log.debug("프로필 Mapping: 프로필 맵핑 성공~~~~~!!!!?");
            model.addAttribute("profile", profile.get());
            return "user/profile";
        }
        log.debug("프로필 Mapping: 프로필 맵핑 실패");
        return "user/profile_failed";
    }

    // 사용자 정보 수정 GET
    @GetMapping("/users/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId) {
        Optional<User> user = repository.findByUserId(userId);

        if (user.isPresent()) {
            log.debug("사용자 정보 수정: 정보수정 브라우저 맵핑 성공~~~~~!!!!?");
            model.addAttribute("user", user.get());
        }
        return "user/updateForm";
    }

    //
    @PutMapping("users/{userId}/update")
    public String updateUser(@ModelAttribute User user, @PathVariable String userId) {
        log.debug("사용자 정보 수정: put 전달 완료");

        Optional<User> temp = repository.findByUserId(userId);
        if (temp.isPresent()) {
            log.debug("사용자 정보 수정: 정보 수정 & 저장 성공");
            user.setId(temp.get().getId());
            repository.update(user);
        }
        return "redirect:/users";
    }
}
