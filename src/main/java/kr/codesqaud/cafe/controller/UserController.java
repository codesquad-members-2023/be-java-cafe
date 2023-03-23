package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        repository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = repository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/form")
    public String showSignUpForm() {
        return "user/form";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable int id, Model model) {
        User user = repository.findById(id);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String showUpdateUserForm(@PathVariable int id, Model model) {
        User updateUser = repository.findById(id);
        model.addAttribute("user", updateUser);

        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute User updateUser, @RequestParam String oldPassword) {
        repository.update(id, updateUser, oldPassword);

        return "redirect:/users";
    }
}
