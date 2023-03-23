package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstConfig;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;
    UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String create(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         Model model
    ) {
        userRepository.join(new User(userId, password, name, email));

        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable String userId,
                          Model model) {
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isEmpty()) return "fail-something";


        model.addAttribute("user", optionalUser.get());
        return "user/profile";
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session,
                             Model model
                             ) {
        User user = (User) session.getAttribute(ConstConfig.SESSION_ID);
        if (user == null) return "fail-something";

        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/update")
    public String update(@RequestParam String curPassword,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         HttpSession session) {
        User user = (User) session.getAttribute(ConstConfig.SESSION_ID);
        if (user == null) return "fail-something";
        if (!user.isSamePassword(curPassword)) return "fail-something";

        if (!userService.update(user, password, name, email)) return "fail-something";
        return "redirect:/user/list";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String password,
                        HttpSession session) {
        Optional<User> optionalUser = userService.login(userId, password);
        if (optionalUser.isEmpty()) return "/user/login-failed";

        session.setAttribute(ConstConfig.SESSION_ID, optionalUser.get());
        session.setAttribute(ConstConfig.SESSION_LOGIN, true);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
