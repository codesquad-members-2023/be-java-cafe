package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final MemoryArticleRepository repository;

    @Autowired
    public ArticleController(MemoryArticleRepository repository) {
        this.repository = repository;
    }

    // 질문하기 POST
    @PostMapping("/questions")
    public String writing(@ModelAttribute Article article) {
        log.info("글쓰기전 5분동안 생각하기!");

        repository.saveArticle(article);
        return "redirect:/";
    }

    // 질문하기 목록 Mapping
    @GetMapping("/")
    public String showBoard(Model model) {
        log.info("내가 싼 글(똥) 목록");

        List<Article> articles = repository.findAllArticle();
        Collections.reverse(articles); // 오래전 글이 아래로 내려 가야함
        model.addAttribute("articles", articles);

        return "index";
    }
}
