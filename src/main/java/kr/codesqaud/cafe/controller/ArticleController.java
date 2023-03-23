package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showArticles(Model model, HttpSession session) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/questions/form")
    public String showQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(@RequestParam String title, @RequestParam String contents, HttpSession session) {
        Article article = new Article((int) session.getAttribute(SessionConstant.LOGIN_USER_ID), title, contents);
        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showDetailedArticle(@PathVariable int index, Model model) {
        Article article = articleRepository.findById(index);
        model.addAttribute("article", article);

        return "qna/show";
    }
}
