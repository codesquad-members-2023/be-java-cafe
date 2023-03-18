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

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println(this.getClass());
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") User user) {
        logger.debug("addUser");
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        logger.debug("getUserList");
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        return "user/list";
    }

    @RequestMapping("/profile/{userId}")
    public String getUser(@PathVariable String userId, Model model) {
        Optional<User> user = userRepository.findById(userId);
        model.addAttribute("profile", user.orElseThrow(IllegalArgumentException::new));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateUser(@PathVariable("userId") String userId, Model model) {
        logger.debug("updateUser : GET");

        Optional<User> updateUser = userRepository.findById(userId);
        // Model 과 View 연결
        model.addAttribute("user", updateUser.orElseThrow());
        return "user/updateForm";
    }

    @PutMapping("/users/{userId}")
    public String updateUser(@ModelAttribute("user") User user) {
        logger.debug("updateUser : PUT");
        userRepository.updateUser(user);
        return "redirect:/users";
    }


}
