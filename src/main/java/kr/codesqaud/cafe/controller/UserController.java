package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {
    MemoryUserRepository memoryUserRepository;

    @Autowired
    public UserController(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    @PostMapping("/create")
    public String create(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         Model model
    ) {
        memoryUserRepository.join(new User(userId, password, name, email));

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = memoryUserRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId,
                          Model model) {
        User user = memoryUserRepository.findUser(userId); // null 포인트 예외 처리 필요
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateForm(@PathVariable String userId,
                         Model model) {
        User user = memoryUserRepository.findUser(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(@PathVariable String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email) {
        User user = memoryUserRepository.findUser(userId);
        // TODO : User 클래스에 비밀번호 체크하는 로직 추가
        if (!Objects.equals(user.getPassword(), password)) return "/fail-something";

        user.setUserId(userId);
        user.setName(name);
        user.setEmail(email);

        return "redirect:/user/list";
    }
}
