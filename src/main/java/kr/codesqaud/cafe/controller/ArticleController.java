package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.ArticleForm;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.MySQLReplyRepository;
import kr.codesqaud.cafe.session.Login;
import kr.codesqaud.cafe.validator.ArticleWritingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    private static final String DELETE = "삭제";
    private static final String UPDATE = "수정";

    private final ArticleRepository articleRepository;
    private final MySQLReplyRepository replyRepository;
    private final ArticleWritingValidator articleWritingValidator;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public ArticleController(ArticleRepository articleRepository, MySQLReplyRepository replyRepository, ArticleWritingValidator articleWritingValidator) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
        this.articleWritingValidator = articleWritingValidator;
    }

    @GetMapping("/")
    public String showArticles(Model model) {
        List<SimpleArticleWithWriter> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);

        return "index";
    }

    @GetMapping("/questions/form")
    public String showQuestionForm(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(@ModelAttribute ArticleForm articleForm, BindingResult bindingResult, @Login User loginUser) {
        articleWritingValidator.validate(articleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "qna/form";
        }

        Article article = new Article(loginUser.getId(), articleForm.getTitle(), articleForm.getContents());
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
    public String deleteArticle(@PathVariable int index, @Login User loginUser) {
        validateUserEqualsWriter(index, loginUser, DELETE);

        articleRepository.delete(index);
        return "redirect:/";
    }

    @GetMapping("/articles/{index}/form")
    public String showArticleUpdateForm(@PathVariable int index, @Login User loginUser, Model model) {
        validateUserEqualsWriter(index, loginUser, UPDATE);

        ArticleWithWriter article = articleRepository.findById(index);
        model.addAttribute("articleId", article.getId());
        model.addAttribute("articleForm", new ArticleForm(article.getTitle(), article.getContents()));

        return "qna/updateForm";
    }

    @PutMapping("/articles/{index}")
    public String updateArticle(@PathVariable int index, @ModelAttribute ArticleForm articleForm, BindingResult bindingResult,
                                @Login User loginUser, Model model) {
        validateUserEqualsWriter(index, loginUser, UPDATE);

        articleWritingValidator.validate(articleForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("articleId", index);
            return "qna/updateForm";
        }

        articleRepository.update(index, articleForm);

        return "redirect:/articles/{index}";
    }

    private void validateUserEqualsWriter(int articleIndex, User loginUser, String action) {
        ArticleWithWriter article = articleRepository.findById(articleIndex);

        if (!loginUser.getUserId().equals(article.getWriter())) {
            throw new IllegalArgumentException("[ERROR] 자신이 작성하지 않은 게시물은 " + action + "할 수 없습니다.");
        }
    }
}
