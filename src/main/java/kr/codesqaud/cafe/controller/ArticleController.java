package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.service.ArticleService;
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
    public String registerArticle(Article article) {
        articleService.writeArticle(article);
        return "redirect:/";
    }

//    @PostMapping("/qna/update")
//    public String updateArticle(Article artice) {
//        articleService.
//        return "redirect:/";
//    }

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
        Article writedArticle = articleService.findOneArticleById(articleId).orElseThrow(() -> new NullPointerException("해당하는 게시글이 없습니다."));
        Member loginMember = (Member) httpSession.getAttribute("sessionedUser");
        if (loginMember.getNickName().equals(writedArticle.getWriter())) {
            model.addAttribute("title", writedArticle.getTitle());
            model.addAttribute("content", writedArticle.getContents());
            return "qna/updateForm";
        }
        return "qna/edite_fail";
    }
}
