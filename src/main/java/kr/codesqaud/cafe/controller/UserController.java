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

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String addUser(@ModelAttribute User user) {
        userRepository.save(user);
        log.error("error log={}", user.getName());
        log.error("error log={}", user.getPassword());
        log.info(" info log={}", user);
        return "/users/form";
    }

    @ResponseBody
    @GetMapping
    public String findUsers(Model model) {
        List<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        log.info(" info log={}", users);
        return "/users/list";
    }

}
