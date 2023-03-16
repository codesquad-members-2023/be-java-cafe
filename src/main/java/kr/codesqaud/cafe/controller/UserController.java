package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.SignUpService;
import kr.codesqaud.cafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final SignUpService signUpService;

    public UserController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    // 회원 가입
    @PostMapping("/create")
    public String addUser(@ModelAttribute User user) {

        signUpService.join(user);

        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        log.info("userList 실행");
        List<User> userList = signUpService.findAll();
        model.addAttribute("userList", userList);

        return "users/list";
    }

    @GetMapping("/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        User user = signUpService.findById(userId).get();
        model.addAttribute("user", user);
        log.info("user 보여주는 메서드 실행");
        return "users/profile";
    }

    @GetMapping("/{userId}/updateUser")
    public String updateUser(@PathVariable String userId, Model model) {

        User findUser = signUpService.findById(userId).get();
        model.addAttribute("findUser", findUser);

        return "users/update_user";
    }

    @PutMapping("/{userId}/updateUser")
    public String updateUserPost(@PathVariable String userId, @ModelAttribute User user) {
        if (!user.getPassword().equals(signUpService.findById(userId).get().getPassword())) {
            return "users/error_page";
        }
        signUpService.updateUser(userId, user);
        return "redirect:/users/list";
    }

    @PostConstruct
    private void postConstruct() {
        User user1 = new User("first", "userPassword1", "userName1", "userEmail1@naver.com");
        User user2 = new User("second", "userPassword2", "userName2", "userEmail2@naver.com");

        signUpService.join(user1);
        signUpService.join(user2);
    }
}
