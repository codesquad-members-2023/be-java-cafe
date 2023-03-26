package kr.codesqaud.cafe.controller;


import kr.codesqaud.cafe.basic.Article;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstConfig;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/createForm")
    public String create(@SessionAttribute(name = ConstConfig.SESSION_ID) User user, Model model) {
        model.addAttribute("user", user);
        return "qna/createForm";
    }

    @PostMapping("/create/{userId}")
    public String create(@PathVariable String userId,
                         @RequestParam String title,
                         @RequestParam String contents) {
        articleRepository.save(new Article(userId, title, contents, Timestamp.valueOf(LocalDateTime.now())));
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
