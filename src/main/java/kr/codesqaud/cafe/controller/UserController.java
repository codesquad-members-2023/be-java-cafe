package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;


    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println(this.getClass());
    }


    @GetMapping("/")
    public String home() {
        return "/index";
    }


    @GetMapping("/form")
    public String get() {
        System.out.println("go form");
        logger.info("info log={}");
        return "user/form";
    }

    @PostMapping("/form")
    public String addUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        logger.info("addUser");
        return "redirect:/getUserList";
    }

    @GetMapping("/getUserList")
    public String getUserList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("list", userList);
        logger.info("getUserList");
        return "user/list";
    }

    @RequestMapping("/profile/{userId}")
    public String getUser(@PathVariable String userId, Model model) {
        Optional<User> user = userRepository.findById(userId);
        model.addAttribute("profile", user.get());
        return "user/profile";
    }

}
