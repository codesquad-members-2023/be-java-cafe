package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String articleForm() {
        return "/qna/form";
    }

    @PostMapping("/qna/form")
    public String articlePost(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) {
        qnaService.postQna(new Article(writer, title, contents));
        System.out.println(new Article(writer, title, contents));
        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("article", qnaService.lookupAllArticles());

        return "index";
    }

    @GetMapping(value = "/articles/{id}")
    public String articleDetails(@PathVariable long id, Model model) {
        model.addAttribute("article", qnaService.lookupById(id).orElseThrow(()->new IllegalArgumentException("[ERROR]")));

        System.out.println(qnaService.lookupById(id).orElseThrow(()->new IllegalArgumentException("[ERROR]")));
        return "/qna/show";
    }
}
