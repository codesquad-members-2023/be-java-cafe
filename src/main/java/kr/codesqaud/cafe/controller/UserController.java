package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.utils.UserInfoException;

@Controller
public class UserController {

    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }

    @GetMapping(value = "/users/{id}")
    public String userProfile(Model model, @PathVariable String id) {
        model.addAttribute("user", joinService.lookupUser(id));
        return "user/profile";
    }

    @GetMapping("/users/list")
    public String userList(Model model) {
        model.addAttribute("user", joinService.lookupAllUser());

        return "user/list";
    }

    @PostMapping("/users/create")
    public String userAdd(@RequestParam String userId, @RequestParam String password,
        @RequestParam String name,
        @RequestParam String email) {
        //POST method, /create form으로 전송하는 요청을 처리
        joinService.join(new User(userId, password, name, email));
        //redirection
        return "redirect:/users/list";
    }

    @GetMapping("/users/{id}/form")
    public String userUpdateForm(@PathVariable String id, Model model, HttpSession session, @ModelAttribute String error) {
        //세션에 로그인 되지 않은 경우 로그인 에러를 발생시킨다.
        if (session.getAttribute("sessionedUser") == null) {
            throw new UserInfoException(UserInfoException.NON_AUTHORIZED_USER_MESSAGE, UserInfoException.NON_AUTHORIZED_USER_CODE);
        }
        String loginId = (String)session.getAttribute("sessionedUser");
        //로그인 아이디가 일치하지 않는 다른 회원의 정보 수정 URL에 접근할 수 없다.
        if (!loginId.equals(id)) {
            throw new UserInfoException(UserInfoException.ILLEGAL_ACCESS_MESSAGE, UserInfoException.ILLEGAL_MODIFICATION_ACCESS_CODE);
        }
        model.addAttribute("userId", id);
        //ERROR 메시지를 전달 받은 경우 회원 정보 수정 뷰에 에러 메시지를 전달한다.
        if (error.length() >= 1) {
            model.addAttribute("error", error);
        }

        return "user/updateForm";
    }

    @PutMapping("/users/{id}/update")
    public String userUpdateCommit(@PathVariable String id, @RequestParam String userId,
        @RequestParam String password,
        @RequestParam String newPassword,
        @RequestParam String name, @RequestParam String email) {
        //id를 저장
        joinService.updateUser(id, password, newPassword, name, email);

        return "redirect:/users/list";
    }

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/users/login")
    public String login(@RequestParam String userId, @RequestParam String password,
        HttpSession session) {
        User sessionedUser = joinService.lookupUser(userId);
        if (!sessionedUser.validate(password)) {
            throw new UserInfoException(UserInfoException.WRONG_PASSWORD_MESSAGE, UserInfoException.WRONG_LOGIN_PASSWORD_CODE);
        }

        session.setAttribute("sessionedUser", sessionedUser.getId());
        return "redirect:/";
    }

    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
