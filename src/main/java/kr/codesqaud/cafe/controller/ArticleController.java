package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.exceptions.UserInfoException;
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

    @GetMapping(value = "/articles/{id}")
    public String articleDetails(@PathVariable long id, Model model) {
        model.addAttribute("article", qnaService.lookupById(id));

        return "qna/show";
    }

    @GetMapping(value = "/articles/{id}/form")
    public String articleModificationForm(@PathVariable long id, Model model, HttpSession httpSession) {
        String writer = qnaService.lookupById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(ArticleInfoException.WRONG_NOT_MATCHING_MESSAGE,
                    ArticleInfoException.WRITER_NOT_MATCHING_CODE);
        }

        return "qna/form";
    }

    @PutMapping(value = "/articles/{id}/form")
    public String articleModify(@PathVariable long id, @RequestParam String title, @RequestParam String contents,
            Model model, HttpSession httpSession) {
        String writer = qnaService.lookupById(id).getWriter();
        if (!httpSession.getAttribute("sessionedUser").equals(writer)) {
            throw new ArticleInfoException(ArticleInfoException.WRONG_NOT_MATCHING_MESSAGE,
                    ArticleInfoException.WRITER_NOT_MATCHING_CODE);
        }


        return "redirect:/";
    }

}
