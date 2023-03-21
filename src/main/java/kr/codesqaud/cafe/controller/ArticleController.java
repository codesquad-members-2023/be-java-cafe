package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.service.QnaService;
import kr.codesqaud.cafe.model.Article;

@Controller
public class ArticleController {
    private final QnaService qnaService;

    public ArticleController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/qna/create")
    public String articlePost(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) {
        qnaService.postQna(new Article(writer, title, contents));

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("article", qnaService.lookupAllArticles());

        return "index";
    }

    @GetMapping(value = "/articles/{id}")
    public String articleDetails(@PathVariable long id, Model model) {
        model.addAttribute("article", qnaService.lookupById(id));

        return "qna/show";
    }

}
