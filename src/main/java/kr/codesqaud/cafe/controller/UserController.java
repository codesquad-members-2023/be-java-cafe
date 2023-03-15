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

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final MemoryUserRepository repository;

    @Autowired
    public UserController(MemoryUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user/form.html")
    public String showForm(){
        return "user/form";
    }

    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        //System.out.println("보냈는가?");
        repository.save(user);
        return "redirect:users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = repository.findAll();
        //System.out.println("받았는가?");
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showUserProfile(Model model, @PathVariable String userId) {
        Optional<User> profile = repository.findById(userId);

        model.addAttribute("profile", profile.get());
        return "user/profile";
    }
}
