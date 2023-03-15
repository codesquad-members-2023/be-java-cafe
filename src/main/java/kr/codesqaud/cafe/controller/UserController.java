package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @PostMapping("/user")
    public String createUser(UserForm form){
        System.out.println(form.toString());
        return "";
    }
}
