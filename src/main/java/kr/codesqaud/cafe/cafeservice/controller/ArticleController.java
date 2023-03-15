package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository repository;

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/questions")
    public String questions(@ModelAttribute Article article) {
        repository.save(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String memberList(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articles/{index}")
    public String showArticle(@PathVariable Long index, Model model) {
        Article article = repository.findOne(index);
        model.addAttribute("article", article);
        return "qna/show";
    }
}
