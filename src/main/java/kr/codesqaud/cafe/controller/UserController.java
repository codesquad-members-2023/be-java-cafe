package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.model.User;

@Controller
public class UserController {
    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping(value = "/users/{id}")
    public String userProfile(Model model, @PathVariable String id) {

        model.addAttribute("user", joinService.lookupUser(id));

        return "/user/profile";
    }

    @GetMapping("/users/list")
    public String userList(Model model) {
        model.addAttribute("user", joinService.lookupAllUser());

        return "/user/list";
    }

    @PostMapping("/users/create")
    public String userAdd(@RequestParam String userId, @RequestParam String password, @RequestParam String name,
            @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        joinService.join(new User(userId, password, name, email));
        //redirection
        return "redirect:/users/list";
    }

    @GetMapping("users/{id}/form")
    public String userUpdateForm(@PathVariable String id, Model model) {
        //id를 전달
        model.addAttribute("userId", id);

        return "user/updateForm";
    }

    @PutMapping("users/{id}/update")
    public String userUpdateCommit(@PathVariable String id, @RequestParam String userId, @RequestParam String password,
            @RequestParam String newPassword,
            @RequestParam String name, @RequestParam String email) {
        //id를 저장
        joinService.updateUser(id, password, newPassword, name, email);

        return "redirect:/users/list";
    }
}
