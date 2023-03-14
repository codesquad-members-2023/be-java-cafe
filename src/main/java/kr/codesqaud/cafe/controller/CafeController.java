package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.domain.UserRepository;
import kr.codesqaud.cafe.user.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/create")
@Slf4j
public class CafeController {
    @Autowired
    private final UserRepository userRepository;

    @PostMapping
    public String joinUser(@RequestParam String userId, @RequestParam String password, @RequestParam String name,
            @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        userRepository.addUser(new User(userId, password, name, email));
        log.info("userRepository = {}", userRepository.toString());
        return "redirect:/index.html";
    }
}
