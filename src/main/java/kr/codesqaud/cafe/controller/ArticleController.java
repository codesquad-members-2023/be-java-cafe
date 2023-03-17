package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    private ArticleService articleService;

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
}
