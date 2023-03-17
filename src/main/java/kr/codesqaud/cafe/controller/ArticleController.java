package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/questions")
    public RedirectView creatQna(@ModelAttribute("Article") Article article) {
        articleService.creatArticle(article);
        System.out.println(article.toString());
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return redirectView;
    }

    @GetMapping("/")
    public String findArticleList(Model model) {
        model.addAttribute("articleList", articleService.findAllArticle());
        return "index";
    }

    @GetMapping("article/{articleId}")
    public String findArticleById(@PathVariable("articleId") int articleId, Model model) {
        model.addAttribute(articleService.findArticleContentById(articleId));
        return "show";
    }
}
