package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired //의존성 주입
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String join(UserForm form) {
        User user = new User(form.getId(), form.getName(), form.getEmail(), form.getPassword());

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String userProfile(Model model, @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("userProfile", user.get());
            return "user/profile";
        }

        return "user/profile_failed";
    }

    @GetMapping("/users/{id}/form")
    public String userProfileCorrection(Model model, @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/updateForm";
        }

        return "user/list";
    }

    @PutMapping("/users/{id}/form")
    public String profileUpdate(UserForm userForm) {
        Optional<User> user = userRepository.findById(userForm.getId());

        if (user.isPresent()) {
            userRepository.save(new User(userForm.getId(), userForm.getName(), userForm.getEmail(), userForm.getPassword()));
            return "redirect:/users";
        }
        //TODO: 비밀번호 일치 시에만 수정하도록
       return "";
    }
}
