package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/questions")
    public String postQuestions(String writer, String title, String contents) {
        Article article = new Article(writer, title, contents);
        logger.info(article.getWriter());
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Article> all = articleRepository.findAll();
        model.addAttribute("list", all);
        model.addAttribute("size", all.size());
        return "index";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("article", articleRepository.findById(id).orElseThrow());
        } catch (NoSuchElementException e) {
            return "qna/show_failed";
        }
        return "qna/show";
    }
}
