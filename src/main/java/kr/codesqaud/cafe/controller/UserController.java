package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userRepository;

    // 회원가입 뷰페이지
    @GetMapping("/add")
    public String addForm() {
        return "user/form";
    }

    // dto 사용 방식
//    @PostMapping("/users")
//    public String addUser(@RequestBody UserJoinRequestDto dto) {
//        userRepository.save(dto.toEntity());
//        return "redirect:/list";
//    }

    // 회원가입
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> all = userRepository.findAll();
        model.addAttribute("list", all);
        return "user/list";
    }

    // 단건 조회
    @GetMapping("/users/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        Optional<User> byId = userRepository.findById(userId);
        //TODO : byId null 일경우 예외처리
        model.addAttribute(byId.get());
        return "user/profile";
    }

}
