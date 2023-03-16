package kr.codesqaud.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomePageController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String welcomePage(){
        log.info("welcomePage 호출");
        return "welcome/index";
    }
}
