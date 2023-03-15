package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.user.User;


@Controller
public class CafeController {
    @Autowired
    private final JoinService joinService;

    public CafeController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping("/user/list")
    public String userList() {
        return "/user/list";
    }
    @GetMapping("/user/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "/user/profile";
    }
    @GetMapping("/user/login")
    public String userLogin() {
        return "/user/login";
    }

    @PostMapping("/user/create")
    public String joinUser(@RequestParam String userId, @RequestParam String password, @RequestParam String name,
            @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        joinService.join(new User(userId, password, name, email));
        //redirection
        return "redirect:/user/list";
    }
}
