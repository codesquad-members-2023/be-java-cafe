package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.exceptions.ArticleInfoException.*;

import javax.servlet.http.HttpSession;

import kr.codesqaud.cafe.exceptions.UserInfoException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Article;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/qna/create")
    public String articlePost(@RequestParam String title, @RequestParam String contents, HttpSession httpSession)
        throws UserInfoException, ArticleInfoException {
        String userId = (String)httpSession.getAttribute("sessionedUser");

        articleRepository.addArticle(new Article(userRepository.findById(userId), title, contents));

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("article", articleRepository.getArticleList());

        return "index";
    }

    @GetMapping(value = "/qna/{id}")
    public String articleDetails(@PathVariable long id, Model model) throws ArticleInfoException {
        model.addAttribute("article", articleRepository.findById(id));

        return "qna/show";
    }

    @GetMapping(value = "/qna/{id}/form")
    public String articleModificationForm(@PathVariable long id, Model model, HttpSession httpSession)
        throws ArticleInfoException {
        String writer = articleRepository.findById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        model.addAttribute("id", id);

        return "qna/modification_form";
    }

    @PutMapping(value = "/qna/{id}/form")
    public String articleModify(@PathVariable long id, @RequestParam String title, @RequestParam String contents,
            HttpSession httpSession) throws ArticleInfoException {
        String writer = articleRepository.findById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        articleRepository.modifyArticle(id, title, contents);

        return "redirect:/";
    }

    @DeleteMapping(value = "/qna/{id}/form")
    public String articleDelete(@PathVariable long id, HttpSession httpSession)
        throws ArticleInfoException {
        String writer = articleRepository.findById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        articleRepository.deleteArticle(id);
        return "redirect:/";
    }

}
