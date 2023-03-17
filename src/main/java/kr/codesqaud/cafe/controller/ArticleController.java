package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    private ArticleRepository articleRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        logger.debug("articleController");
    }

//    @GetMapping("/questions")
//    public String
    @PostMapping("/questions")
    public String addContents(@ModelAttribute Article article){
        logger.debug("addContents");
        articleRepository.addContents(article);
        System.out.println(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getContentList(Model model) {
        logger.debug("getContentList");
        List<Article> contentList = articleRepository.findAll();
        model.addAttribute("contents", contentList);
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String getContent(@PathVariable("id") long id, Model model) {
        Article contentList = articleRepository.findByIndex(id).get();
        model.addAttribute("articles", contentList);
        return "qna/show";
    }
}
