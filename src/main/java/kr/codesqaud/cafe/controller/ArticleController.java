package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
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
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new NoSuchElementException("test");
        }
        model.addAttribute("article", article.get());
        return "qna/show";
    }
}
