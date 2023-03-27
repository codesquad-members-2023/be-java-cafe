package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.SessionUtil;
import kr.codesqaud.cafe.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final SessionUtil sessionUtil;

    @Autowired
    public UserController(UserService userService, SessionUtil sessionUtil) {
        this.userService = userService;
        this.sessionUtil = sessionUtil;
    }

    // 회원가입 POST (서비스 수정)
    @PostMapping("/users")
    public String signUp(@ModelAttribute User user) {
        log.debug("회원가입 POST: 보내졌는가?");

        if (userService.join(user)) {
            return "redirect:users";
        }
        return "user/form_failed";
    }

    // 사용자 목록 Mapping (서비스 수정)
    @GetMapping("/users")
    public String showUsers(Model model, HttpSession session) {
        log.debug("사용자 목록 Mapping: 받았는가?");

        List<User> users = userService.findAllUser();
        User sessionUser = (User) sessionUtil.getUserInfo(session);

        if (sessionUser != null) {
            model.addAttribute("loginUser", sessionUser);
        }
        model.addAttribute("users", users);
        return "user/list";
    }

    // 프로필 Mapping (서비스 수정)
    @GetMapping("/users/{userId}")
    public String showUserProfile(Model model, @PathVariable String userId) {
        // 프로필 유무 확인후 성공/실패 넘겨주기
        if (userCheck(userService.findByUserId(userId), model)) {
            log.debug("프로필 Mapping: 성공");
            return "user/profile";
        }
        log.debug("프로필 MappingT: 실패");
        return "user/profile_failed";
    }

    // 사용자 정보 수정 GET (서비스 수정)
    @GetMapping("/users/{userId}/form")
    public String updateForm(Model model, @PathVariable String userId, HttpSession session) {
        // 세션 검증
        User sessionUser = (User) sessionUtil.getUserInfo(session);
        if (sessionUser == null || !sessionUser.getUserId().equals(userId)) {
            return "error";
        }

        // 회원정보 가져오기(검증후 일치하는)
        if (userCheck(userService.findByUserId(userId), model)) {
            log.debug("사용자 정보 수정 GET: 성공");
        }
        return "user/updateForm";
    }

    // 사용자 정보 수정 PUT (서비스 수정)
    @PutMapping("users/{userId}/update")
    public String updateUser(@ModelAttribute User user, @PathVariable String userId, String password) {
        log.debug("사용자 정보 수정: put 전달 완료");

        User passwordCheckUser = userService.passwordCheck(userId, password);
        // 비밀번호 체크 실패
        if (passwordCheckUser == null) {
            log.debug("사용자 정보 수정 PUT: 비밀번호 틀림 ㅋ");
            return "user/updateForm_failed";
        }
        // 체크 성공
        log.debug("사용자 정보 수정 PUT: 정보 수정 성공");
        user.setId(passwordCheckUser.getId());
        userService.update(user);
        return "redirect:/users";
    }

    // 유저 검증(프로필, 수정)에 사용
    private boolean userCheck(Optional<User> userInfo, Model model) {
        AtomicBoolean check = new AtomicBoolean(false);
        userInfo.ifPresent(user -> {
            model.addAttribute("user", user);
            check.set(true);
        });

        return check.get();
    }
}
