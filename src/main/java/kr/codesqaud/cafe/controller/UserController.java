package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired //의존성 주입
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public String join(UserForm form) {
        User user = new User(form.getId(), form.getName(), form.getEmail(), form.getPassword());

        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String userProfile(Model model, @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("userProfile", user.get());
            return "user/profile";
        }

        return "user/profile_failed";
    }

    @GetMapping("/users/{id}/form")
    public String userProfileCorrection(Model model, @PathVariable String id, HttpSession session) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            Object value = session.getAttribute("loginUser");
            if (value != null) {
                User sessionUser = (User) value;
                if (sessionUser.getId().equals(user.get().getId())) {
                    model.addAttribute("user", user.get());
                    return "user/updateForm";
                }
            }
        }
        //수정하려는 사용자 정보와 로그인한 사용자 정보가 다를 때
        return "error";
    }

    @PutMapping("/users/{id}/form")
    public String profileUpdate(UserForm userForm, Model model, @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            if (user.get().matchPassword(userForm.getPrePassword())) {
                userRepository.save(new User(userForm.getId(), userForm.getName(), userForm.getEmail(), userForm.getPassword()));
                return "redirect:/users";
            }
            model.addAttribute(SessionConst.LOGIN_USER, user.get());
            return "user/updateForm_failed";
        }
        //id가 db에 존재하지 않을 경우 홈 화면으로
        return "index";
    }

    @PostMapping("/login")
    public String login(UserForm userForm, HttpSession session) {
        Optional<User> user = userRepository.findById(userForm.getId());

        if (user.isPresent()) {
            if (user.get().getPassword().equals(userForm.getPassword())) {
                session.setAttribute(SessionConst.LOGIN_USER, user.get());

                return "redirect:/users";
            }
        }
        return "user/login_failed";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "index";
    }
}
