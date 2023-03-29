package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/articles")
    public String post(ArticleForm form, HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Article article = new Article(loginUser.getId(), form.getTitle(), form.getContent());

        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("articles", articleRepository.findAll());

        return "index";
    }

    @GetMapping("/articles/{id}")
    public String articleShow(Model model, @PathVariable int id) {
        model.addAttribute("article", articleRepository.findById(id));
        return "qna/show";
    }
}
