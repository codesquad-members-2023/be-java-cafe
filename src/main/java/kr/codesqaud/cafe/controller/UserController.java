package kr.codesqaud.cafe.controller;

import java.util.List;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String findUser() {
        return "list2";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public List<User> getUser() {
        return userService.findUserAll();
    }

    @GetMapping("users/{userId}")
    public String getProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute(userId);
        return "profile2";
    }

    @GetMapping("/findById/{userId}")
    @ResponseBody
    public List<User> findById(@PathVariable("userId") String userId) {
        return userService.findUserByUserId(userId);
    }
}
