package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MySQLReplyRepository;
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

    private static final String DELETE = "삭제";
    private static final String UPDATE = "수정";

    private final ArticleRepository articleRepository;
    private final MySQLReplyRepository replyRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(ArticleRepository articleRepository, MySQLReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    @GetMapping("/")
    public String showArticles(Model model) {
        List<SimpleArticleWithWriter> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/questions/form")
    public String showQuestionForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(@RequestParam String title, @RequestParam String contents, HttpSession session) {
        Article article = new Article((int) session.getAttribute(SessionConstant.LOGIN_USER_ID), title, contents);
        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/articles/{index}")
    public String showDetailedArticle(@PathVariable int index, Model model) {
        ArticleWithWriter article = articleRepository.findById(index);
        List<ReplyWithUser> replies = replyRepository.findByArticleId(index);
        model.addAttribute("article", article);
        model.addAttribute("replies", replies);

        return "qna/show";
    }

    @DeleteMapping("/articles/{index}")
    public String deleteArticle(@PathVariable int index, HttpSession session) {
        validateUserEqualsWriter(index, session, DELETE);

        articleRepository.delete(index);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}/form")
    public String showArticleUpdateForm(@PathVariable int index, HttpSession session, Model model) {
        validateUserEqualsWriter(index, session, UPDATE);

        ArticleWithWriter article = articleRepository.findById(index);
        model.addAttribute("article", article);

        return "qna/updateForm";
    }

    @PutMapping("/articles/{index}")
    public String updateArticle(@PathVariable int index, @RequestParam String title, @RequestParam String contents, HttpSession session) {
        validateUserEqualsWriter(index, session, UPDATE);
        Article updateArticle = new Article(0, title, contents);

        articleRepository.update(index, updateArticle);

        return "redirect:/articles/{index}";
    }

    private void validateUserEqualsWriter(int articleIndex, HttpSession session, String action) {
        String loginUserName = (String) session.getAttribute(SessionConstant.LOGIN_USER_NICKNAME);
        ArticleWithWriter article = articleRepository.findById(articleIndex);

        if (!loginUserName.equals(article.getWriter())) {
            throw new IllegalArgumentException("[ERROR] 자신이 작성하지 않은 게시물은 " + action + "할 수 없습니다.");
        }
    }
}
