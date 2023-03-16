package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.MemoryArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private final MemoryArticleRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(MemoryArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showArticles(Model model) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @PostMapping("/questions")
    public String question(@ModelAttribute Article article) {
        repository.save(article);

        log.info("{}", article.getId());
        log.info("{}", article.getWriter());
        log.info("{}", article.getTitle());
        log.info("{}", article.getContents());

        return "redirect:/";
    }
}
