package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/create")
    public RedirectView creatUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/users");
        return redirectView;
    }

    @GetMapping("/users")
    public String findUserList(Model model) {
        model.addAttribute("userList", userService.findUserAll());
        return "list2";
    }

    @GetMapping("users/{userId}")
    public String findUserProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userList", userService.findUserByUserId(userId));
        return "profile2";
    }

    @GetMapping("users/{userId}/form")
    public String findUserProfileEdit(@PathVariable("userId") String userId, Model model) {
        model.addAttribute(userService.findUserByUserId(userId));
        return "updateForm";
    }

    @PutMapping("users/update")
    public RedirectView updateUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.updateUser(user);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/users");
        return redirectView;
    }
}
