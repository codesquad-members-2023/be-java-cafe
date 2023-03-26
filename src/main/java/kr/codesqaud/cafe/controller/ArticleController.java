package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.exceptions.ArticleInfoException.*;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.service.JoinService;
import kr.codesqaud.cafe.service.QnaService;
import kr.codesqaud.cafe.model.Article;

@Controller
public class ArticleController {
    private final QnaService qnaService;
    private final JoinService joinService;

    public ArticleController(QnaService qnaService, JoinService joinService) {
        this.qnaService = qnaService;
        this.joinService = joinService;
    }

    @PostMapping("/qna/create")
    public String articlePost(@RequestParam String title, @RequestParam String contents, HttpSession httpSession) {
        String userId = (String)httpSession.getAttribute("sessionedUser");

        qnaService.postQna(new Article(joinService.lookupUser(userId), title, contents));

        return "redirect:/";
    }

    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("article", qnaService.lookupAllArticles());

        return "index";
    }

    @GetMapping(value = "/qna/{id}")
    public String articleDetails(@PathVariable long id, Model model) {
        model.addAttribute("article", qnaService.lookupById(id));

        return "qna/show";
    }

    @GetMapping(value = "/qna/{id}/form")
    public String articleModificationForm(@PathVariable long id, Model model, HttpSession httpSession) {
        String writer = qnaService.lookupById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        model.addAttribute("id", id);

        return "qna/modification_form";
    }

    @PutMapping(value = "/qna/{id}/form")
    public String articleModify(@PathVariable long id, @RequestParam String title, @RequestParam String contents,
            HttpSession httpSession) {
        String writer = qnaService.lookupById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        qnaService.modifyQna(id, title, contents);

        return "redirect:/";
    }

    @DeleteMapping(value = "/qna/{id}/form")
    public String articleDelete(@PathVariable long id, HttpSession httpSession) {
        String writer = qnaService.lookupById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(UNAUTHORIZED_MODIFICATION_MESSAGE,
                    WRITER_NOT_MATCHING_CODE);
        }
        qnaService.deleteQna(id);
        return "redirect:/";
    }

}
