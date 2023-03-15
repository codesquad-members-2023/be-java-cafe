package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.UserMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserLogicController {
    UserMemoryRepository userMemoryRepository;

    @Autowired
    public UserLogicController(UserMemoryRepository userMemoryRepository) {
        this.userMemoryRepository = userMemoryRepository;
    }

    @GetMapping("/form")
    public String form() {
        log.info("hi");
        return "user/form";
    }

    @PostMapping("/create")
    public String create(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         Model model
    ) {
        userMemoryRepository.join(new User(userId, password, name, email));

        return "redirect:/user";
    }

    @GetMapping
    public String list(Model model) {
        List<User> users = userMemoryRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId,
                          Model model) {
        User user = userMemoryRepository.findUser(userId); // null 포인트 예외 처리 필요
        model.addAttribute("user", user);
        return "user/profile";
    }
}
