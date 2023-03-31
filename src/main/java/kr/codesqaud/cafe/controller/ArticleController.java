package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {

    private ArticleService articleService;
    private final int MINUS_INDEX = 1;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna/article")
    public String askQuestion(HttpSession httpSession) {
        Member member = (Member) httpSession.getAttribute("sessionedUser");
        if (member == null) {
            return "redirect:/user/login";
        }
        return "qna/form";
    }

    @PostMapping("/qna/ask")
    public String registerArticle(Article article, HttpSession httpSession) {
        Member loginMember = (Member) httpSession.getAttribute("sessionedUser");
        article.setWriter(loginMember.getNickName());
        articleService.writeArticle(article);
        return "redirect:/";
    }

    @PostMapping("/qna/{articleId}/update")
    public String updateArticle(@PathVariable long articleId, Article article) {
        article.setArticleId(articleId);
        articleService.updateArticle(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String printArticleList(Model model) {
        model.addAttribute("article", articleService.findArticles());
        model.addAttribute("total", articleService.getTotalNumberOfArticles());
        return "index";
    }

    @GetMapping("/qna/{articleId}")
    public String printDetailArticle(@PathVariable int articleId, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("sessionedUser") == null) {
            return "redirect:/user/login";
        };
        Article article = articleService.findOneArticleById(articleId).get();
        model.addAttribute("article", article);
        return "qna/show";
    }

    @GetMapping("/article/{articleId}/edite")
    public String editeArticle(@PathVariable long articleId, HttpSession httpSession, Model model) {
        Article writedArticle = articleService.findOneArticleById(articleId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        Member loginMember = (Member) httpSession.getAttribute("sessionedUser");
        if (loginMember.getNickName().equals(writedArticle.getWriter())) {
            model.addAttribute("article", writedArticle);
            return "qna/updateForm";
        }
        return "qna/edite_fail";
    }

    @DeleteMapping("/article/{articleId}/delete")
    public String deleteArticle(@PathVariable long articleId, HttpSession httpSession) {
        Article writedArticle = articleService.findOneArticleById(articleId).orElseThrow(() -> new EmptyResultDataAccessException(1));
        Member loginMember = (Member) httpSession.getAttribute("sessionedUser");
        if (loginMember.getNickName().equals(writedArticle.getWriter())) {
            articleService.deleteArticle(articleId);
            return "redirect:/";
        }
        return "qna/delete_fail";
    }
}
