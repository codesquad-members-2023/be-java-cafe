package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String showArticles(Model model, HttpSession session) {
        List<Article> articles = repository.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("loginUserId", session.getAttribute(SessionConstant.LOGIN_USER_ID));

        return "index";
    }

    @GetMapping("/questions/form")
    public String showQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(@ModelAttribute Article article) {
        repository.save(article);

        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showDetailedArticle(@PathVariable int index, Model model) {
        Article article = repository.findById(index);
        model.addAttribute("article", article);

        return "qna/show";
    }
}
