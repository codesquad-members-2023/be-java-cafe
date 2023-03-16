package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class UserController {

    private final MemoryUserRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(MemoryUserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    void init() {
        repository.save(new User("Hyun", "1234", "황현", "ghkdgus29@naver.com"));
        repository.save(new User("Yoon", "4321", "황윤", "ghkddbs28@naver.com"));
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

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable int id, Model model) {
        User user = repository.findByUserId(id);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String showUpdateUserForm(@PathVariable int id, Model model) {
        User updateUser = repository.findByUserId(id);
        model.addAttribute("user", updateUser);

        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute User user) {
        User findUser = repository.findByUserId(id);

        findUser.setName(user.getName());
        findUser.setPassword(user.getPassword());
        findUser.setEmail(user.getEmail());

        return "redirect:/users";
    }
}
