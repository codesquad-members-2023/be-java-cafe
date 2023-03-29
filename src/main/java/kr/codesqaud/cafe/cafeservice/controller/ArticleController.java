package kr.codesqaud.cafe.cafeservice.controller;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @DeleteMapping("/questions/{id}delete")
    public String deleteArticle(@PathVariable Long id) {
        repository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable Long id, Model model, HttpSession session) {
        Object attribute = session.getAttribute(SessionConst.LOGIN_MEMBER);
        LoginSessionUtils sessionUtils = (LoginSessionUtils) attribute;
        if (sessionUtils.getId() == id) {
            Optional<Article> findArticle = repository.findById(id);
            model.addAttribute("article", findArticle.orElseThrow());
            return "qna/updateForm";
        }
        return "error";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(@RequestParam String title, @RequestParam String content,
                                @PathVariable Long id) {
        //TODO 자기가 작성한 글이 아니면 수정 할 수 없다.
        Optional<Article> findArticle = repository.findById(id);
        repository.update(findArticle, title, content);
        return "redirect:/";
    }
}
