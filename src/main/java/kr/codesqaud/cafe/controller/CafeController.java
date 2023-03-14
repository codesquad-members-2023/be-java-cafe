package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.user.User;


@Controller
@RequestMapping("/user/create")
public class CafeController {
    @Autowired
    private final JoinService joinService;

    public CafeController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping
    public String joinUser(@RequestParam String userId, @RequestParam String password, @RequestParam String name,
            @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        joinService.join(new User(userId, password, name, email));
        //redirection
        return "redirect:/templates/user/list.html";
    }
}
