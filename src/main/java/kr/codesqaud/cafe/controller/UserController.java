package kr.codesqaud.cafe.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.user.User;


@Controller
public class UserController {
    @Autowired
    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping(value = "/users/{id}")
    public String userList(Model model, @PathVariable String id) {
        model.addAttribute("user", joinService.lookupUser(id).get());

        return "/user/profile";
    }
    @GetMapping("/users/list")
    public String userList(Model model) {
        model.addAttribute("user", joinService.lookupAllUser());

        return "/user/list";
    }

    @PostMapping("/users/create")
    public String joinUser(@RequestParam String userId, @RequestParam String password, @RequestParam String name,
            @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        joinService.join(new User(userId, password, name, email));
        //redirection
        return "redirect:/users/list";
    }
}
