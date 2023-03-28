package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import kr.codesqaud.cafe.dto.article.ArticleUpdateDTO;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.validation.article.ArticleNewFormValidator;
import kr.codesqaud.cafe.validation.article.ArticleUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ArticleNewFormValidator articleNewFormValidator;
    private final ArticleUpdateValidator articleUpdateValidator;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ArticleNewFormValidator articleNewFormValidator, ArticleUpdateValidator articleUpdateValidator) {
        this.articleRepository = articleRepository;
        this.articleNewFormValidator = articleNewFormValidator;
        this.articleUpdateValidator = articleUpdateValidator;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Article> allArticle = articleRepository.findAllArticle();
        model.addAttribute(allArticle);

        return "welcome/index";
    }

    @GetMapping("/qna/qna_form")
    public String writeArticle(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("username", user.getName());
        model.addAttribute("article", new Article());

        return "qna/qna_form";
    }

    @PostMapping("/qna/questions")
    public String addArticle(@ModelAttribute("article") ArticleFormDTO article, BindingResult bindingResult, Model model, HttpSession session) {
        articleNewFormValidator.validate(article, bindingResult);
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
        Article article = articleRepository.findArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 질문글입니다."));
        String userName = articleRepository.findUsernameByArticleUserId(article.getUserId());
        model.addAttribute("userName", userName);
        model.addAttribute("article", article);

        return "qna/show";
    }


    @GetMapping("/qna/update_article/{articleId}")
    public String updateArticleForm(@PathVariable int articleId, Model model, HttpSession session) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("없는 질문글입니다."));
        User user = (User) session.getAttribute("user");
        if (user.getUserId().equals(article.getUserId())) {
            model.addAttribute("article", article);
            model.addAttribute("user", user);
            return "qna/update_article";
        }

        throw new IllegalArgumentException("글 작성자만 수정할 수 있습니다.");
    }

    @PutMapping("/qna/update_article/{articleId}")
    public String updateArticle(@ModelAttribute("article") ArticleUpdateDTO article, BindingResult bindingResult, @PathVariable int articleId, Model model) {
        articleUpdateValidator.validate(article, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", articleId);
            return "qna/update_article";
        }

        articleRepository.updateArticle(article.getTitle(), article.getContents(), articleId);

        return "redirect:/qna/show/{articleId}";
    }

    @DeleteMapping("/qna/show/{articleId}/delete")
    public String deleteArticle(@PathVariable int articleId, HttpSession session) {
        Article findArticle = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("없는 질문글입니다."));
        User user = (User) session.getAttribute("user");

        if (user.getUserId().equals(findArticle.getUserId())) {
            articleRepository.deleteArticle(articleId);
            return "redirect:/";
        }

        throw new IllegalArgumentException("글 작성자만 삭제할 수 있습니다");
    }
}
