package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.validator.UserSignUpValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserSignUpValidator userSignUpValidator;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(UserRepository userRepository, UserSignUpValidator userSignUpValidator) {
        this.userRepository = userRepository;
        this.userSignUpValidator = userSignUpValidator;
    }

    @InitBinder
    public void addValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(userSignUpValidator);
    }

    @PostMapping("/users")
    public String signUp(@Validated @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/form";
        }

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/users/form")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable int id, Model model) {
        User user = userRepository.findById(id);
        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String showUpdateUserForm(@PathVariable int id, Model model) {
        User updateUser = userRepository.findById(id);
        model.addAttribute("user", updateUser);

        return "user/updateForm";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute User updateUser, @RequestParam String oldPassword) {
        userRepository.update(id, updateUser, oldPassword);

        return "redirect:/users";
    }
}
