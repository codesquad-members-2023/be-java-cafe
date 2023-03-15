package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.domain.QnaService;
import kr.codesqaud.cafe.user.Article;

@Controller
public class ArticleController {
    private QnaService qnaService;

    @Autowired
    public ArticleController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/qna/form")
    public String qna() {
        return "/qna/form";
    }
    @PostMapping("/qna/form")
    public String question(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) {
        new Article(writer, title, contents);

        return "redirect:/";
    }
}
