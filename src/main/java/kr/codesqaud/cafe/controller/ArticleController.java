package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.Article;
import kr.codesqaud.cafe.repository.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        log.info(allArticle.get(1).getContents());

        return "welcome/index";
    }
    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article) {
        log.info("addArticle 호출");
        log.info(article.getContents());

        articleService.writeArticle(article);

        return "redirect:/";
    }



    @PostConstruct
    public void postConstruct() {
        Article article1 = new Article("writer1", "title1", "content1");
        Article article2 = new Article("writer2", "title2", "content2");
        Article article3 = new Article("writer3", "title3", "content3");

        articleService.writeArticle(article1);
        articleService.writeArticle(article2);
        articleService.writeArticle(article3);
    }

}
