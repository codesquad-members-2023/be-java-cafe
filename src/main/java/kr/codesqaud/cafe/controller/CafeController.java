package kr.codesqaud.cafe.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeController {
    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }
}
