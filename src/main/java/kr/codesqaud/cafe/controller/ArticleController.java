package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.repository.article.ArticleRepository;

import kr.codesqaud.cafe.util.SessionConst;
import kr.codesqaud.cafe.util.ValidateConst;
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

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/qna")
    public String addArticle(@ModelAttribute Article article, Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }

        articleRepository.save(article);
        log.debug("debug log={}", article.getContents());
        return "redirect:/qna/list";
    }

    @GetMapping("/qna/form")
    public String orderQnaForm(HttpSession httpSession, Model model) {

        log.info("orderQnaForm, httpSession={}", httpSession.getAttribute("loggedInId"));

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }

        return "qna/form";
    }

    @GetMapping(value = {"/qna/list", "/"})
    public String findArticleList(Model model) {
        List<Article> articleList = articleRepository.findAllArticles();
        model.addAttribute("articleList", articleList);
        log.info("createdAt={}", articleList.get(0).getFormattedCreatedAt());
        return "qna/list";
    }

    @GetMapping("/qna/{id}")
    public String findArticle(@PathVariable int id, Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("loggedInId") == null) {
            model.addAttribute("errorMessage", ValidateConst.UNKNOWN_USER);
            return "util/error";
        }

        Article article = articleRepository.findByArticleId(id);
        model.addAttribute("article", article);

        return "qna/show";
    }

    @GetMapping("qna/{articleId}/edit")
    public String orderEditForm(@PathVariable long articleId, HttpSession httpSession, Model model) {

        log.info("articleId = {}", articleId);

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConst.NOT_YOURS);
            return "util/error";
        }

        Article article = articleRepository.findByArticleId(articleId);
        model.addAttribute("article", article);
        log.info("filterd article = {}", article.getId());

        return "qna/edit";
    }

    @PutMapping("qna/{articleId}")
    public String updateArticle(@PathVariable long articleId, @ModelAttribute Article article, HttpSession httpSession, Model model) {

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConst.NOT_YOURS);
            return "util/error";
        }

        return "redirect:/qna/list";
    }

    @DeleteMapping("/qna/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpSession httpSession, Model model) {

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConst.NOT_YOURS);
            return "util/error";
        }

        articleRepository.deleteAriticle(articleId);

        return "redirect:/qna/list";
    }

    private boolean validateIdentity(long articleId, HttpSession httpSession) {

        Article targetArticle = articleRepository.findByArticleId(articleId);

        return targetArticle.getWriter() == (long) httpSession.getAttribute(SessionConst.LOGIN_USERID);
    }
}
