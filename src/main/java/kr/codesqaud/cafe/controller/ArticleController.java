package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.exceptions.ArticleInfoException.*;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import kr.codesqaud.cafe.exceptions.UserInfoException;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.ReplyRepository;
import kr.codesqaud.cafe.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Article;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository,
            ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }

    @PostMapping("/qna/create")
    public String articlePost(@RequestParam String title, @RequestParam String contents, HttpSession httpSession)
            throws UserInfoException, ArticleInfoException {
        String userId = (String)httpSession.getAttribute("sessionedUser");

        articleRepository.addArticle(
                new Article(userRepository.findById(userId), title, contents, LocalDateTime.now()));

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
        model.addAttribute("reply", replyRepository.getReplyList());

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

    @PostMapping(value = "/qna/{articleId}/reply")
    public String replyForm(@PathVariable long articleId, @RequestParam String replyContents,
            RedirectAttributes redirectAttributes, HttpSession httpSession)
            throws ArticleInfoException, UserInfoException {
        String userId = articleRepository.findById(articleId).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(userId)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }

        replyRepository.addReply(
                new Reply(userRepository.findById(userId), replyContents, LocalDateTime.now(),
                        articleId));

        redirectAttributes.addFlashAttribute("articleId", articleId);

        return "redirect:/qna/{articleId}";
    }

}
