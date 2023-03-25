package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.validation.ArticleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ArticleRepository articleRepository;
    private final ArticleValidator articleValidator;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleValidator articleValidator) {
        this.articleRepository = articleRepository;
        this.articleValidator = articleValidator;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Article> allArticle = articleRepository.findAllArticle();
        model.addAttribute(allArticle);

        return "welcome/index";
    }

    @GetMapping("/qna/qna_form")
    public String writeArticle(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        model.addAttribute("username", user.getName());
        model.addAttribute("article", new Article());

        return "qna/qna_form";
    }

    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article, BindingResult bindingResult, Model model, HttpSession session) {
        log.info("article id ={}", article.getUserId());
        articleValidator.validate(article, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", article.getUserId());
            return "qna/qna_form";
        }
        User user = (User) session.getAttribute("user");
        article.setUserId(user.getUserId());

        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        Article article = articleRepository.findArticleById(id);
        model.addAttribute(article);

        return "qna/show";
    }


    @GetMapping("/qna/update_article/{articleId}") // TODO: 에러페이지 생성
    public String updateArticleForm(@PathVariable int articleId, Model model, HttpSession session) {
        Article article = articleRepository.findArticleById(articleId);
        User user = (User) session.getAttribute("user");

        if (user.getName().equals(article.getUserId())) {
            model.addAttribute("article", article);
            return "qna/update_article";
        }

        throw new IllegalArgumentException("글 작성자만 수정할 수 있습니다.");
    }

    @PutMapping("/qna/update_article/{articleId}")
    public String updateArticle(@ModelAttribute Article article, BindingResult bindingResult, @PathVariable int articleId) {
        articleValidator.validate(article, bindingResult);
        if (bindingResult.hasErrors()) {
            return "qna/update_article";
        }

        articleRepository.updateArticle(article.getTitle(), article.getContents(), articleId);

        return "redirect:/qna/show/{articleId}";
    }

    @DeleteMapping("/qna/show/{articleId}/delete") // TODO: 에러페이지 생성하기
    public String deleteArticle(@PathVariable int articleId, HttpSession session) {
        Article findArticle = articleRepository.findArticleById(articleId);

        User user = (User) session.getAttribute("user");

        if (user.getName().equals(findArticle.getUserId())) {
            articleRepository.deleteArticle(articleId);
            return "redirect:/";
        }

        log.info("글 작성자만 삭제할 수 있습니다.");
        throw new IllegalArgumentException("글 작성자만 삭제할 수 있습니다");
    }
}
