package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showArticles(Model model, HttpSession session) {
        List<Article> articles = articleRepository.findAll();
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
        Article article = articleRepository.findById(index);
        model.addAttribute("article", article);

        return "qna/show";
    }

    @DeleteMapping("/articles/{index}")
    public String deleteArticle(@PathVariable int index, HttpSession session) {
        checkUserEqualsWriter(index, session, "삭제");

        articleRepository.delete(index);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}/form")
    public String showArticleUpdateForm(@PathVariable int index, HttpSession session, Model model) {
        checkUserEqualsWriter(index, session, "수정");

        Article article = articleRepository.findById(index);
        model.addAttribute("article", article);

        return "qna/updateForm";
    }

    @PutMapping("/articles/{index}")
    public String updateArticle(@PathVariable int index, @RequestParam String title, @RequestParam String contents, HttpSession session) {
        checkUserEqualsWriter(index, session, "수정");
        Article updateArticle = new Article(0, title, contents);

        articleRepository.update(index, updateArticle);

        return "redirect:/articles/{index}";
    }

    private void checkUserEqualsWriter(int articleIndex, HttpSession session, String action) {
        User loginUser = userRepository.findById((int) session.getAttribute(SessionConstant.LOGIN_USER_ID));
        Article article = articleRepository.findById(articleIndex);

        if (!loginUser.getUserId().equals(article.getWriter())) {
            throw new IllegalArgumentException("[ERROR] 자신이 작성하지 않은 게시물은 " + action + "할 수 없습니다.");
        }
    }
}
