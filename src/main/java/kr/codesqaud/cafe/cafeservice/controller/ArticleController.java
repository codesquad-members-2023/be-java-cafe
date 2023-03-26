package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleRepository repository;
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String memberList(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @PostMapping("/questions")
    public String questions(@ModelAttribute Article article) {
        log.debug("article{}=", article);
        repository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showArticle(@PathVariable Long index, Model model) {
        try {
            Optional<Article> findArticle = repository.findById(index);
            if (findArticle.isPresent()) {
                model.addAttribute("article", findArticle.get());
                return "qna/show";
            } else {
                return "fail";
            }
        } catch (NoSuchElementException e) {
            return "fail";
        }
    }
}
