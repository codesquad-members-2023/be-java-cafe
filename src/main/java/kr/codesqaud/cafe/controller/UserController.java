package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.SignUpService;
import kr.codesqaud.cafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final SignUpService signUpService;

    public UserController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    // 회원 가입
    @PostMapping("/user/create")
    public String addUser(@RequestParam String userId,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam String email,
                          Model model) {

        log.info("addUser 호출");

        User user = new User(userId, password, name, email);
        signUpService.join(user);
        model.addAttribute("user", user);

        return "redirect:/user/list";
    }

}
