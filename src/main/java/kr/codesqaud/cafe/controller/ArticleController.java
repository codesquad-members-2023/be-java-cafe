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

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute Article article, BindingResult bindingResult, Model model) {

        articleValidator.validate(article, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", article.getWriter());
            return "qna/qna_form";
        }
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

        String userId = (String) session.getAttribute("userId");
        model.addAttribute("userId", userId);
        model.addAttribute("article", new Article());

        return "qna/qna_form";
    }

    @GetMapping("/qna/update_article/{articleId}") // TODO: 에러페이지 생성
    public String updateArticleForm(@PathVariable int articleId, Model model, HttpSession session) {
        Article article = articleRepository.findArticleById(articleId);
        String userId = (String) session.getAttribute("userId");

        if (userId.equals(article.getWriter())) {
            model.addAttribute("article", article);
            return "qna/update_article";
        }
        log.info("글 작성자만 수정할 수 있습니다.");
        return "redirect:/";
    }

    @PutMapping("/qna/update_article/{articleId}")
    public String updateArticle(@ModelAttribute Article article, @PathVariable int articleId) {
        articleRepository.updateArticle(article.getTitle(), article.getContents(), articleId);

        return "redirect:/qna/show/{articleId}";
    }

    @DeleteMapping("/qna/show/{articleId}/delete") // TODO: 에러페이지 생성하기
    public String deleteArticle(@PathVariable int articleId, HttpSession session) {
        Article findArticle = articleRepository.findArticleById(articleId);

        String userId = (String) session.getAttribute("userId");

        if (userId.equals(findArticle.getWriter())) {
            articleRepository.deleteArticle(articleId);
            return "redirect:/";
        }

        log.info("글 작성자만 삭제할 수 있습니다.");
        return "redirect:/";
    }
}
