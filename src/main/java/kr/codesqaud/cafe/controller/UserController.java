package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.users.UserRepository;
import kr.codesqaud.cafe.util.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String test() {
        return "users/login";
    }

    @PostMapping("/login")
    public String validateAndLogin(@RequestParam String userId, @RequestParam String password, HttpSession session) {

        log.info("userId, password [{}][{}]", userId, password);
        Optional<User> dbUser = userRepository.findUserWithMatchedPassword(userId, password);

        if (dbUser.isEmpty()) {
            return "users/login_failed";
        }

        User user = dbUser.get();

        session.setAttribute(SessionConstant.LOGIN_USERID, user.getId());
        return "redirect:/qna/list";
    }

    @GetMapping("/logout")
    public String orderLogout(HttpSession httpSession) {
        if (httpSession.getAttribute(SessionConstant.LOGIN_USERID) != null) {
            httpSession.invalidate();
        }
        return "redirect:/qna/list";
    }

    @GetMapping("/form")
    public String findUserFormPage() {
        return "users/form";
    }

    @PostMapping
    public String addUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String findUsers(Model model) {
        List<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String findUser(@PathVariable long id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute(user);
        log.debug("debug log={}", user.getName());
        return "users/profile";
    }

    @GetMapping("/{loggedInId}/edit")
    public String findUser(HttpSession httpSession, Model model) {

        long loggedInUserId = (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);
        User user = userRepository.findUserById(loggedInUserId);
        model.addAttribute(user);
        log.debug("debug log={}", user.getName());

        return "users/edit";
    }

    @PutMapping("/{loggedInId}/edit")
    public String editUser(HttpSession httpSession, @ModelAttribute User user) {

        long loggedInUserId = (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);
        User dbUser = userRepository.findUserById(loggedInUserId);

        if (!dbUser.getPassword().equals(user.getPassword())) {
            return "redirect:/users/{loggedInId}/edit";
        }

        userRepository.update(user, loggedInUserId);

        return "redirect:/qna/list";
    }
}
