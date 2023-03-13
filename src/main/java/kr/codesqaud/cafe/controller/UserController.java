package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {


    @GetMapping("/users")
    private String viewForm() {
        return "/user/form";
    }


}
