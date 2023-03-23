package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserDto;
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

    @PostMapping("/user/creation")
    public String creatUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String findUserList(Model model) {
        model.addAttribute("userDto", userService.findUserAll());
        return "list2";
    }

    @GetMapping("/users/{userId}")
    public String findUserProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userService.findUserByUserId(userId));
        return "profile2";
    }

    @GetMapping("/users/{userId}/form")
    public String findUserProfileEdit(@PathVariable("userId") String userId, Model model) {
        model.addAttribute(userService.findUserByUserId(userId));
        return "updateForm";
    }

    @PutMapping("/users/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto) {
        userService.updateUser(userDto);
        return "redirect:/";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute("userDto") UserDto userDto, HttpSession session) {
        if(userService.findUserByPassword(userDto)) {
            session.setAttribute("userId", userDto.getUserId());
            return "redirect:/";
        }
        return "redirect:/user/login_failed.html";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:/";
    }
}
