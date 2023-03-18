package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/add")
    public String addForm() {
        return "user/form";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String users(Model model) {
        List<User> users = userRepository.showAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User findUser = userRepository.findByUserId(userId);
        model.addAttribute("user", findUser);
        return "user/profile";
    }

    /**
     * 테스트 용 데이터 추가
     */
    @PostConstruct
    public void init() {
        userRepository.save(new User("GOMUNGNAM", "247597", "고건호", "rhrjsgh97@gmail.com"));
        userRepository.save(new User("Roy", "123456", "이승로", "roy@gmail.com"));
        userRepository.save(new User("Birdie", "123456", "임동현", "birdie@gmail.com"));
        userRepository.save(new User("Hana", "1234", "왕하나", "hana@gmail.com"));
    }
}
