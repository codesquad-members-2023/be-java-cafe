package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.basic.UserDTO;
import kr.codesqaud.cafe.config.ConstantConfig;
import kr.codesqaud.cafe.exception.gobalExeption.NotFoundException;
import kr.codesqaud.cafe.exception.userException.*;
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

    @GetMapping("/create")
    public String create() {
        return "user/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute UserDTO userDTO,
                         Model model) {
        userRepository.join(userDTO);

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
        if (optionalUser.isEmpty()) throw new NotFoundException("유저의 정보를 불러오는데 실패했습니다.");


        model.addAttribute("user", optionalUser.get());
        return "user/profile";
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute(ConstantConfig.SESSION_ID);
        if (user == null) throw new UserSessionExpireException();

        model.addAttribute("user", user);
        return "user/update";
    }

    @PutMapping("/update/{userId}")
    public String update(@ModelAttribute UserDTO userDTO,
                         @PathVariable String userId,
                         @RequestParam String curPassword,
                         HttpSession session) {
        User user = (User) session.getAttribute(ConstantConfig.SESSION_ID);
        if (user == null) throw new UserSessionExpireException();
        if (!user.isSamePassword(curPassword)) throw new UserUpdateException("잘못된 비밀번호 입니다.");
        if (!userService.update(userDTO)) throw new UserUpdateException("업데이트에 실패했습니다.");

        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String update() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO,
                        @RequestParam(defaultValue = "/") String requestURL,
                        HttpSession session) {
        Optional<User> optionalUser = userService.login(userDTO);
        if (optionalUser.isEmpty()) throw new UserLoginException();

        session.setAttribute(ConstantConfig.SESSION_ID, optionalUser.get());
        session.setAttribute(ConstantConfig.SESSION_LOGIN, true);
        return "redirect:" + requestURL;
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
