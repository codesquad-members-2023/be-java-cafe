package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    ArticleRepository articleRepository;

    public ArticleController(@Qualifier ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

    }

    @PostMapping("/create")
    public String create(@RequestParam String writer,
                         @RequestParam String title,
                         @RequestParam String contents) {
        articleRepository.save(new Article(writer, title, contents, Timestamp.valueOf(LocalDateTime.now())));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articles = new ArrayList<>(articleRepository.findAll());

        model.addAttribute("articles", articles);
        return "qna/list";
    }

    @GetMapping("show/{articleId}")
    public String show(@PathVariable int articleId,
                       Model model) {
        int articleIndex = articleId;
        Article article = articleRepository.findByIndex(articleIndex);
        model.addAttribute(article);
        return "qna/show";
    }

}
