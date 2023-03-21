package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Article> allArticle = articleRepository.findAllArticle();
        log.info(String.valueOf(allArticle.get(0).getId()));
        model.addAttribute(allArticle);

        return "welcome/index";
    }
    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article) {
        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        Article article = articleRepository.findArticleById(id);
        model.addAttribute(article);

        return "qna/show";
    }


}
