package kr.codesqaud.cafe.controller;

import java.util.List;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    private final UserService userService;

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

    @GetMapping("users")
    public String findUser() {
        userService.searchUserAll();
        return "user/list.html";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public List<User> returnUser() {
        return userService.returnUserList();
    }
}
