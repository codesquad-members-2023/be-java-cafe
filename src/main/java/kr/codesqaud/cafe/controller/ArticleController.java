package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.repository.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class ArticleController {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {

        List<Article> allArticle = articleService.findAllArticle();
        model.addAttribute(allArticle);

        return "welcome/index";
    }
    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article) {
        log.info("addArticle 호출");
        log.info(article.getContents());

        articleService.writeArticle(article);

        return "redirect:/";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {

        Article article = articleService.findArticleById(id).get();
        model.addAttribute(article);

        return "qna/show";
    }


    @PostConstruct
    public void postConstruct() {
        Article article1 = new Article("first", "title1", "content1");
        Article article2 = new Article("second", "title2", "content2");

        articleService.writeArticle(article1);
        articleService.writeArticle(article2);
    }

}
