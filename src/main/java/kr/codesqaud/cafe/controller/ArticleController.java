package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.model.Article;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.repository.ReplyDto;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ReplyService replyService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/openForm")
    public String openForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String creatArticle(@ModelAttribute("Article") Article article) {
        articleService.creatArticle(article);
        return "redirect:/";
    }

    @GetMapping("/")
    public String findArticleList(Model model) {
        model.addAttribute("articleDto", articleService.findAllArticle());
        return "index";
    }

    @GetMapping("/article/{articleId}")
    public String findArticleById(@PathVariable("articleId") int articleId, Model model) {
        model.addAttribute(articleService.findArticleContentById(articleId));
        model.addAttribute(replyService.findReplyAllByArticleId(articleId));
        logger.warn(replyService.findReplyAllByArticleId(articleId).toString());
        return "user/show";
    }

    @GetMapping("/article/{articleId}/edit")
    public String updateArticle(@PathVariable("articleId") int articleId, Model model, HttpSession session) {
        if (!articleService.findArticleContentById(articleId).getUserId().equals(session.getAttribute("userId"))) {
            throw new IllegalArgumentException("다른 사람의 게시글은 수정할 수 없습니다.");
        }
        model.addAttribute(articleService.findArticleContentById(articleId));
        return "qna/updateForm";
    }

    @PutMapping("/editSubmit")
    public String putUpdateArticle(Article article, String articleId) {
        articleService.updateArticle(article, articleId);
        return "redirect:/article/" + articleId;
    }

    @DeleteMapping("/article/delete")
    public String deleteArticle(String articleId, String userId, HttpSession session) {
        if (!userId.equals(session.getAttribute("userId"))) {
            throw new IllegalArgumentException("다른 사람의 게시글은 삭제할 수 없습니다.");
        }
        if(replyService.findReplyAllByArticleId(Integer.parseInt(articleId)).size()
                != replyService.findReplyByArticleIdAndUserId(Integer.parseInt(articleId), userId).size()) {
            throw new IllegalArgumentException("다른 사람의 댓글이 있는 게시물은 삭제할 수 없습니다.");
        }
        articleService.deleteArticle(Integer.parseInt(articleId));
        return "redirect:/";
    }

    @PostMapping("/article/reply/create")
    public String createReply(Reply reply) {
        replyService.createReply(reply);
        return "redirect:/article/" + reply.getArticleId();
    }

    @GetMapping("/article/reply/{replyId}/update")
    public String updateReplyForm(@PathVariable("replyId") String replyId, Model model, HttpSession session) {
        if(!replyService.findReplyByReplyId(replyId).getUserId().equals(session.getAttribute("userId"))) {
            throw new IllegalArgumentException("다른 사람의 댓글은 수정할 수 없습니다.");
        }
        model.addAttribute(replyService.findReplyByReplyId(replyId));
        return "user/updateShow";
    }

    @PutMapping("/article/reply/updateSubmit")
    public String updateReply(@RequestParam String oldReplyId, Reply reply) {
        replyService.updateReply(reply, oldReplyId);
        return "redirect:/article/" + reply.getArticleId();
    }

    @DeleteMapping("/article/reply/delete")
    public String deleteReply(ReplyDto replyDto, HttpSession session) {
        if(!replyDto.getUserId().equals(session.getAttribute("userId"))) {
            throw new IllegalArgumentException("다른 사람의 댓글은 삭제할 수 없습니다.");
        }
        replyService.deleteReply(replyDto);
        return "redirect:/article/" + replyDto.getArticleId();
    }
}
