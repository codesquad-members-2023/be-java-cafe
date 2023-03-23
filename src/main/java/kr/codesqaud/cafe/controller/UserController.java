package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.validation.UserJoinValidator;
import kr.codesqaud.cafe.validation.UserLoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MemberRepository memberRepository;
    private final UserLoginValidator userLoginValidator;
    private final UserJoinValidator userJoinValidator;

    @Autowired
    public UserController(MemberRepository memberRepository, UserLoginValidator userLoginValidator, UserJoinValidator userJoinValidator) {
        this.memberRepository = memberRepository;
        this.userLoginValidator = userLoginValidator;
        this.userJoinValidator = userJoinValidator;
    }

    @GetMapping("/form")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "users/form";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute User user, BindingResult bindingResult) {
        userJoinValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/form";
        }

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
    public String updateUser(@PathVariable String userId, Model model, HttpSession session, HttpServletResponse response) {

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

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "redirect:/users/list";
    }

    @PutMapping("/{userId}/updateUser") // TODO : DTO 고려해보기, 로직을 service로 넘기기
    public String updateUserPost(@ModelAttribute User user) {
        if (memberRepository.findById(user.getUserId()).getPassword().equals(user.getPassword())) {
            return "users/error_page";
        }

        memberRepository.updateUser(user);
        return "users/list";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

    @PostMapping("/process_login") //TODO: 아이디를 잘못 입력했을 경우 예외처리, 틀릴 경우에만 에러 페이지 나오도록 수정, 에러 페이지, 에러 정보 담도록 스프링, 서블릿
    public String loginUser(@ModelAttribute User loginUser, BindingResult bindingResult, HttpSession session) {
        userLoginValidator.validate(loginUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/login";
        }

        User user = memberRepository.findById(loginUser.getUserId());
        session.setAttribute("user", user);
        log.info("로그인 성공");
        return "redirect:/users/list";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();

        return "redirect:/users/list";
    }
}
