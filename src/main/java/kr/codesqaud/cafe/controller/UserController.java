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
    public String addUser(@RequestParam String userId,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam String email
                          ) {
        User user = new User.Builder()
                .userId(userId)
                .password(password)
                .name(name)
                .email(email)
                .build();

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

    @PostConstruct
    private void postConstruct(Model model){
        User user1 = new User.Builder()
                .userId("first")
                .password("first")
                .name("first")
                .email("first@naver.com")
                .build();
        User user2 = new User.Builder()
                .userId("second")
                .password("second")
                .name("second")
                .email("second@naver.com").build();

        signUpService.join(user1);
        signUpService.join(user2);
    }
}
