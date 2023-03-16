package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/user/list")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String users(Model model) {
        List<User> users = userRepository.showAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     * 테스트 용 데이터 추가
     */
    @PostConstruct
    public void init() {
        userRepository.save(new User("GOMUNGNAM", "247597", "고건호", "rhrjsgh97@gmail.com"));
        userRepository.save(new User("Roy", "123456", "이승로", "roy@gmail.com"));
        userRepository.save(new User("Birdie", "123456", "임동현", "birdie@gmail.com"));
    }
}
