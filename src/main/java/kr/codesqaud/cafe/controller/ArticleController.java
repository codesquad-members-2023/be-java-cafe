package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String articles(Model model) {
        List<Article> articles = articleRepository.showAllArticles();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/qna/questions")
    public String writeForm() {
        return "qna/form";
    }

    @PostMapping("/qna/questions")
    public String saveQuestion(@ModelAttribute("article") Article article) {
        articleRepository.write(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{articleSequence}")
    public String articleShow(@PathVariable Long articleSequence, Model model) {
        Article findArticle = articleRepository.findByArticleSequence(articleSequence);
        model.addAttribute("article", findArticle);
        return "qna/show";
    }
}
