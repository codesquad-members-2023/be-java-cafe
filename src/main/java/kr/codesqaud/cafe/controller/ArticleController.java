package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/questions")
    public String post(ArticleForm form) {
        Article article = new Article(form.getWriter(), form.getTitle(), form.getContent());

        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("articles", articleRepository.findAll());

        return "index";
    }

    @GetMapping("/articles/{index}")
    public String articleShow(Model model, @PathVariable int index) {
        model.addAttribute("article", articleRepository.findByIndex(index));
        return "qna/show";
    }
}
