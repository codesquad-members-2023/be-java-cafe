package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final MemberRepository memberRepository;


    public UserController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute User user) {
        memberRepository.save(user);

        return "redirect:/users/list";
    }

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> userList = memberRepository.findAll();
        model.addAttribute("userList", userList);

        return "users/list";
    }

    @GetMapping("/{userId}")
    public String showUser(@PathVariable String userId, Model model) {
        User user = memberRepository.findById(userId);
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/{userId}/updateUser")
    public String updateUser(@PathVariable String userId, Model model, HttpSession session) {

        User user = memberRepository.findById(userId);
        Object value = session.getAttribute("user");

        if (value != null) {
            User loginedUser = (User) value;
            if (loginedUser.getUserId().equals(user.getUserId())) {
                memberRepository.updateUser(user);
                model.addAttribute("user", user);

                return "users/update_user";
            }
        }


        return "redirect:/users/list";
    }

    @PutMapping("/{userId}/updateUser") // TODO : DTO 고려해보기, 로직을 service로 넘기기
    public String updateUserPost(@ModelAttribute User user, HttpSession session) {


        if (memberRepository.findById(user.getUserId()).getPassword().equals(user.getPassword())) {
            return "users/error_page";
        }

        memberRepository.updateUser(user);
        return "users/list";
    }

    @GetMapping("/login")
    public String goLoginPage() {
        return "users/login";
    }

    @PostMapping("/login") //TODO: 아이디를 잘못 입력했을 경우 예외처리
    public String loginUser(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        User user = memberRepository.findById(userId);

        if (user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/users/list";
        }

        return "users/error_page";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();

        return "redirect:/users/list";
    }
}
