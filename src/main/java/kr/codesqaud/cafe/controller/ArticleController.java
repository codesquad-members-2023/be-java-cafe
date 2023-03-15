package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    // 글쓰기
    @GetMapping("/qna/form.html")
    public String showQnaForm() {
        return "qna/form";
    }
}
