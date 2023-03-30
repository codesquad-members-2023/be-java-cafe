package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/articles")
    public String post(ArticleForm form, HttpSession session) {
        User loginUser = UserSession.getAttribute(session);
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
    public String articleShow(Model model, @PathVariable String id) {
        model.addAttribute("article", articleRepository.findById(id));
        return "qna/show";
    }

    @GetMapping("/articles/{articleId}/form")
    public String articleCorrection(Model model, @PathVariable String articleId, HttpSession session) {
        Article article = articleRepository.findById(articleId);
        //TODO: db에서 오류 EmptyResultDataAccessException
        //수정하려는 id와 세션 id 일치할 때
        if (UserSession.isEqualSessionIdTo(article.getWriter(), session)) {
            model.addAttribute("article", article);
            return "qna/updateForm";
        }
        return "error";
    }

    @PutMapping("/articles/{articleId}/form")
    public String articleUpdate(ArticleForm articleForm, @PathVariable String articleId) {
        Article article = articleRepository.findById(articleId);

        articleRepository.update(articleForm.getTitle(), articleForm.getContent(), Long.toString(article.getId()));
        return "redirect:/";
    }

    @DeleteMapping("/articles/{articleId}")
    public String articleDelete(@PathVariable String articleId, HttpSession session) {
        if (UserSession.isEqualSessionIdTo(articleId, session)) {
            articleRepository.delete(articleId);
            return "redirect:/";
        }
        return "error";
    }
}
