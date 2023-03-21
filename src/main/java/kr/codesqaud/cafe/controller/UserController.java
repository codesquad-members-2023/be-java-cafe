package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.UserRepository;
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

    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

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
        log.trace("사용자 ID: {}", user.getUserId());
        log.trace("사용자 이름: {}", user.getName());
        log.trace("사용자 email: {}", user.getEmail());
        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String users(Model model) {
        List<User> users = userRepository.showAllUsers();
        model.addAttribute("users", users);
        log.trace("사용자 수: {}", users.size());
        return "user/list";
    }

    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User findUser = userRepository.findByUserId(userId);
        model.addAttribute("user", findUser);
        return "user/profile";
    }

    @GetMapping("/update/{userId}")
    public String editUserForm(@PathVariable String userId, Model model) {
        model.addAttribute("user", userRepository.findByUserId(userId));
        return "user/updateForm";
    }

    @PutMapping("/update/{userId}")
    public String edit(@PathVariable String userId, @ModelAttribute User user) {
        userRepository.updateUser(userId, user);
        return "redirect:/users/list";
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
