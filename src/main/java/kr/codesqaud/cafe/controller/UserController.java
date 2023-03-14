package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class UserController {

    private final MemoryUserRepository repository;

    @Autowired
    public UserController(MemoryUserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    void init() {
        repository.save(new User("Hyun", "1234", "황현", "ghkdgus29@naver.com"));
        repository.save(new User("Yoon", "4321", "황윤", "ghkddbs28@naver.com"));
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/users/form")
    public String showRegistrationForm() {

        return "user/form";
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

    @GetMapping("/users/{userId}")
    public String showUserProfile(@PathVariable String userId, Model model) {
        User user = repository.findByUserId(userId);
        model.addAttribute("user", user);

        return "user/profile";
    }
}
