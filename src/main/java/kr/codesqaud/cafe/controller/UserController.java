package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserJoinRequestDto;
import kr.codesqaud.cafe.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/user/form")
    public String addForm() {
        return "user/form";
    }

    @PostMapping("/users")
    public String addUser(UserJoinRequestDto dto) throws IllegalAccessException {
        userService.join(dto.toEntity());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> all = userService.findAll();
        model.addAttribute("list", all);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        model.addAttribute("profile", userService.findUser(userId));
        return "user/profile";
    }

}
