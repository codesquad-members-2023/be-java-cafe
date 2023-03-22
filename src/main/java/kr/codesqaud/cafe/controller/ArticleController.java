package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Article> allArticle = articleRepository.findAllArticle();
        model.addAttribute(allArticle);

        return "welcome/index";
    }

    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article) {
        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        Article article = articleRepository.findArticleById(id);
        model.addAttribute(article);

        return "qna/show";
    }

    @GetMapping("/qna/qna_form")
    public String writeArticle(HttpSession session, Model model) {
        Object value = session.getAttribute("user");
        if (value != null) {
            User user = (User) value;
            model.addAttribute("user", user);
        }

        return "qna/qna_form";
    }

    @GetMapping("/qna/update_article/{articleId}") // TODO: 상태코드 변경
    public String updateArticleForm(@PathVariable int articleId, Model model, HttpSession session) {
        Article article = articleRepository.findArticleById(articleId);
        Object value = session.getAttribute("user");

        if (value != null) {
            User user = (User) value;
            if (user.getUserId().equals(article.getWriter())) {
                model.addAttribute("article", article);
                return "qna/update_article";
            }
        }
        log.info("글 작성자만 수정할 수 있습니다.");
        return "redirect:/";
    }

    @PutMapping("/qna/update_article/{articleId}")
    public String updateArticle(@ModelAttribute Article article, @PathVariable int articleId) {
        articleRepository.updateArticle(article.getTitle(), article.getContents(), articleId);

        return "redirect:/qna/show/{articleId}";
    }

    @DeleteMapping("/qna/show/{articleId}/delete") // TODO : 상태코드 변경
    public String deleteArticle(@PathVariable int articleId, HttpSession session) {
        Article findArticle = articleRepository.findArticleById(articleId);

        Object value = session.getAttribute("user");

        if (value != null) {
            User user = (User) value;
            if (user.getUserId().equals(findArticle.getWriter())) {
                articleRepository.deleteArticle(articleId);
                return "redirect:/";
            }
        }

        log.info("글 작성자만 삭제할 수 있습니다.");
        return "redirect:/";
    }
}
