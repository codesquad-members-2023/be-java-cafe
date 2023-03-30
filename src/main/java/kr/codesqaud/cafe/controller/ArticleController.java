package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.SessionUser;
import kr.codesqaud.cafe.exception.ExceptionStatus;
import kr.codesqaud.cafe.exception.InvalidAuthorityException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.constant.ConstUrl.REDIRECT_INDEX;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleController(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/questions")
    public String postQuestions(HttpSession httpSession, String title, String contents) {
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);
        Member member = memberRepository.findById(sessionUser.getId());
        Article article = new Article(member, title, contents);
        articleRepository.save(article);
        return REDIRECT_INDEX;
    }

    @DeleteMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, HttpSession httpSession) throws InvalidAuthorityException {
        Article exArticle = articleRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(exArticle.getWriterId())) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }

        articleRepository.delete(id);
        return REDIRECT_INDEX;
    }

    @GetMapping("/articles/{id}/update")
    public String updateArticleForm(@PathVariable long id, Model model, HttpSession httpSession) throws InvalidAuthorityException {
        Article article = articleRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(article.getWriterId())) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }

        model.addAttribute("article", article);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{id}/update")
    public String updateArticle(Article newArticle, @PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession httpSession) throws InvalidAuthorityException {
        Article exArticle = articleRepository.findById(id);
        SessionUser sessionUser = SessionUser.getSessionUser(httpSession);

        if (!sessionUser.equals(exArticle.getWriterId())) {
            throw new InvalidAuthorityException(ExceptionStatus.NO_SESSION_USER);
        }

        articleRepository.update(exArticle, newArticle);
        redirectAttributes.addFlashAttribute("id", id);
        return "redirect:/articles/{id}";
    }

    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleRepository.findById(id));
        return "qna/show";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", articleRepository.findAll());
        return "index";
    }
}
