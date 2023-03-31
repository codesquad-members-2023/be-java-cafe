package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.repository.article.ArticleRepository;

import kr.codesqaud.cafe.repository.comment.CommentRepository;
import kr.codesqaud.cafe.util.SessionConstant;
import kr.codesqaud.cafe.util.ValidateConstant;
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
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/qna")
    public String addArticle(@RequestParam String title,
                             @RequestParam String contents,
                             Model model, HttpSession httpSession) {

        if (httpSession.getAttribute(SessionConstant.LOGIN_USERID) == null) {
            model.addAttribute("errorMessage", ValidateConstant.UNKNOWN_USER);
            return "util/error";
        }

        long writer = (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);
        articleRepository.save(new Article(writer, title, contents));

        return "redirect:/qna/list";
    }

    @GetMapping("/qna/form")
    public String orderQnaForm(HttpSession httpSession, Model model) {

        if (httpSession.getAttribute(SessionConstant.LOGIN_USERID) == null) {
            return "redirect:/users/login";
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

        if (httpSession.getAttribute(SessionConstant.LOGIN_USERID) == null) {
            model.addAttribute("errorMessage", ValidateConstant.UNKNOWN_USER);
            return "util/error";
        }

        Article article = articleRepository.findByArticleId(id);
        model.addAttribute("article", article);

        List<Comment> commentList = commentRepository.findAllCommentsByArticleId(id);
        model.addAttribute("comments", commentList);

        int numOfComments = commentRepository.findNumOfCommentsByArticleId(id);
        model.addAttribute("numOfComments", numOfComments);

        return "qna/show";
    }

    @GetMapping("qna/{articleId}/edit")
    public String orderEditForm(@PathVariable long articleId, HttpSession httpSession, Model model) {

        log.info("articleId = {}", articleId);

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConstant.NOT_YOURS);
            return "util/error";
        }

        Article article = articleRepository.findByArticleId(articleId);
        model.addAttribute("article", article);
        log.info("filterd article = {}", article.getId());

        return "qna/edit";
    }

    @PutMapping("qna/{articleId}")
    public String updateArticle(@PathVariable long articleId,
                                @RequestParam String title,
                                @RequestParam String contents,
                                HttpSession httpSession, Model model) {

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConstant.NOT_YOURS);
            return "util/error";
        }

        long writer = (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);

        articleRepository.updateArticle(new Article(writer, title, contents));

        return "redirect:/qna/list";
    }

    @DeleteMapping("/qna/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpSession httpSession, Model model) {

        if (!validateIdentity(articleId, httpSession)) {
            model.addAttribute("errorMessage", ValidateConstant.NOT_YOURS);
            return "util/error";
        }

        articleRepository.deleteArticle(articleId);

        return "redirect:/qna/list";
    }

    private boolean validateIdentity(long articleId, HttpSession httpSession) {

        Article targetArticle = articleRepository.findByArticleId(articleId);

        return targetArticle.getWriter() == (long) httpSession.getAttribute(SessionConstant.LOGIN_USERID);
    }
}
