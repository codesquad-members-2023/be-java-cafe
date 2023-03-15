package kr.codesqaud.cafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    // 글쓰기
    @GetMapping("/qna/form.html")
    public String showQnaForm() {
        log.info("글쓰기로 들어왔는감?!");

        return "qna/form";
    }
}
