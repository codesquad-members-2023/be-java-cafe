package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.LoginForm;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.validator.UserLoginValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final UserLoginValidator userLoginValidator;

    @Autowired
    public LoginController(UserRepository userRepository, UserLoginValidator userLoginValidator) {
        this.userRepository = userRepository;
        this.userLoginValidator = userLoginValidator;
    }

    @InitBinder
    public void addValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(userLoginValidator);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        User loginUser = userRepository.findByUserId(loginForm.getUserId());
        session.setAttribute(SessionConstant.LOGIN_USER, loginUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
