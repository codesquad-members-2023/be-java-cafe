package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.article.ArticleFormDTO;
import kr.codesqaud.cafe.dto.article.ArticleUpdateDTO;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.util.SessionUtil;
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
    private final ReplyRepository replyRepository;
    private final ArticleNewFormValidator articleNewFormValidator;
    private final ArticleUpdateValidator articleUpdateValidator;
    private final SessionUtil sessionUtil;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, ReplyRepository replyRepository, ArticleNewFormValidator articleNewFormValidator, ArticleUpdateValidator articleUpdateValidator, SessionUtil sessionUtil) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
        this.articleNewFormValidator = articleNewFormValidator;
        this.articleUpdateValidator = articleUpdateValidator;
        this.sessionUtil = sessionUtil;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        List<Article> allArticle = articleRepository.findAllArticle();
        for (Article article : allArticle) {
            List<Reply> replies = replyRepository.findAllReplyByArticleId(article.getId());
            article.setReplyList(replies);
        }
        model.addAttribute(allArticle);

        return "welcome/index";
    }

    @GetMapping("/qna/qna_form")
    public String writeArticleForm (Model model) {
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
        User user = sessionUtil.getSessionedUser(session);
        article.setUserId(user.getUserId());
        articleRepository.save(article);

        return "redirect:/";
    }

    @GetMapping("/qna/show/{id}")
    public String showArticle(@PathVariable int id, Model model) {
        Article article = articleRepository.findArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 질문글입니다."));
        String userName = articleRepository.findUsernameByArticleUserId(article.getUserId());

        List<Reply> replyList = replyRepository.findAllReplyByArticleId(id);

        model.addAttribute("userName", userName);
        model.addAttribute("article", article);
        model.addAttribute("replyList", replyList);

        return "qna/show";
    }


    @GetMapping("/qna/update_article/{articleId}")
    public String updateArticleForm(@PathVariable int articleId, Model model, HttpSession session) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("없는 질문글입니다."));
        User user = sessionUtil.getSessionedUser(session);
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

        String articleUserId = findArticle.getUserId();
        if (canDelete(session, articleUserId, articleId)) {
            return "redirect:/";
        }

        throw new IllegalArgumentException("글 작성자만 삭제할 수 있습니다");
    }

    private boolean canDelete(HttpSession session, String articleUserId, int articleId) {
        User user = sessionUtil.getSessionedUser(session);

        if (user.getUserId().equals(articleUserId)) {
            checkArticleReply(articleId, user.getUserId());
            articleRepository.deleteArticle(articleId);
            removeAllReply(articleId);
            return true;
        }
        return false;
    }

    // 자신의 질문글에 다른 사람의 댓글이 있을 시 예외 처리
    private void checkArticleReply(int articleId, String userId) {
        List<Reply> allReplyByArticleId = replyRepository.findAllReplyByArticleId(articleId);

        long count = allReplyByArticleId.stream()
                .filter(replyId -> !replyId.getUserId().equals(userId))
                .count();

        if (count > 0) {
            throw new IllegalArgumentException("다른 사람의 댓글이 있을 경우 삭제할 수 없습니다.");
        }
    }

    private void removeAllReply(int articleId) {
        List<Reply> allReplyByArticleId = replyRepository.findAllReplyByArticleId(articleId);

        for (Reply reply : allReplyByArticleId) {
            replyRepository.delete(reply.getId());
        }
    }
}
