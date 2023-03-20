package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.repository.memoryRepository.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;

    @Autowired
    public UserController(@Qualifier UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String create(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         Model model
    ) {
        userRepository.join(new User(userId, password, name, email));

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId,
                          Model model) {
        User user = userRepository.findUser(userId);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateForm(@PathVariable String userId,
                         Model model) {
        User user = userRepository.findUser(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email) {
        User user = new User(userId, password, name , email);
        if (!userRepository.update(user))  return "/fail-something";

        return "redirect:/user/list";
    }
}
