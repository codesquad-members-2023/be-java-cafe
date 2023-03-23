package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {

    private ArticleService articleService;
    private final int MINUS_INDEX = 1;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/qna/ask")
    public String registerArticle(Article article) {
        articleService.writeArticle(article);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String printArticleList(Model model) {
        model.addAttribute("article", articleService.findArticles());
        model.addAttribute("total", articleService.getTotalNumberOfArticles());
        return "/index";
    }

    @GetMapping("/qna/{articleId}")
    public String printDetailArticle(@PathVariable int articleId, Model model) {
        Article article = articleService.findOneArticleById(articleId).get();

        model.addAttribute("article", article);
        return "/qna/show";
    }
}
