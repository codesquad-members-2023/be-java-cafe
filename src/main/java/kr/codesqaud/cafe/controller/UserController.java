package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/users/form")
    public String showRegistrationForm() {

        return "user/form";
    }

    @PostMapping("/users")
    public String signUp(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email) {

        System.out.println("userId = " + userId);
        System.out.println("password = " + password);
        System.out.println("name = " + name);
        System.out.println("email = " + email);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers() {

        return "user/list";
    }
}
