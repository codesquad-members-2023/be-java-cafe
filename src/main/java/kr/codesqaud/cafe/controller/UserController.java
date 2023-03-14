package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        System.out.println(this.getClass());
    }


    @GetMapping("/")
    public String home() {
        return "index";
    }


    @GetMapping("/users/form")
    public String get() {
        System.out.println("hi");
        return "/user/form";
    }

    @PostMapping("/users/form")
    public String addUser(@ModelAttribute("user") User user) {
        userService.join(user);
        return "redirect:/getUserList";
    }

    @GetMapping("/getUserList")
    public String getUserList(Model model) {
        List<User> userList = userService.findMembers();
        model.addAttribute("list",userList);
        return "user/list";
    }

}
