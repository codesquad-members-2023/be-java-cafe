package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.service.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final ArticleService articleService;
    private final ReplyService replyService;
    private final SessionUtil sessionUtil;

    @Autowired
    public ArticleController(ArticleService articleService, ReplyService replyService, SessionUtil sessionUtil) {
        this.articleService = articleService;
        this.replyService = replyService;
        this.sessionUtil = sessionUtil;
    }

    // 질문하기 POST (서비스)
    @PostMapping("/questions")
    public String writing(@ModelAttribute Article article, HttpSession session) {
        log.debug("질문하기: 글쓰기전 5분동안 생각하기!");

        // 현재 로그인 유저Id 게시글에 set
        User sessionUser = (User) sessionUtil.getUserInfo(session);
        article.setWriter(sessionUser.getUserId());

        articleService.write(article);
        return "redirect:/";
    }

    // 질문 상세보기 Mapping (서비스)
    @GetMapping("/articles/{id}")
    public String showBoardDetails(Model model, @PathVariable long id, HttpSession session) {
        Optional<Article> article = articleService.findByArticleId(id);

        // 질문글 유무 확인후 성공/실패 넘겨주기
        if (article.isPresent()) {
            log.debug("질문글 Mapping: 맵핑 성공!!!!");
            model.addAttribute("article", article.get());

            // 댓글 부분
            List<Reply> replies = replyService.findAllReply(article.get().getId());
            model.addAttribute("articleId", article.get().getId());
            model.addAttribute("replySize", replies.size());
            model.addAttribute("reply", replies);

            return "qna/show";
        }
        log.debug("질문글 Mapping: 맵핑 실패 ㅠㅠㅠ");
        return "qna/show_failed";
    }

    // 질문 수정하기 Mapping (서비스)
    @GetMapping("/articles/{id}/update")
    public String editing(HttpSession session, @PathVariable long id) {
        User sessionUser = (User) sessionUtil.getUserInfo(session);
        if (!articleService.sessionCheck(id, sessionUser)) {
            log.debug("질문 수정 검증: 본인글이 아닙니다!!! 떽!!!!");
            return "error";
        }

        log.debug("질문 수정 검증: 본인글이니 어서 수정하시죠 도죠도죠");
        return "qna/updateForm";
    }

    // 게시글 수정 PUT (서비스)
    @PutMapping("/articles/{id}/update")
    public String updateArticle(@ModelAttribute Article article, @PathVariable long id, HttpSession session) {
        User sessionUser = (User) sessionUtil.getUserInfo(session);
        if (!articleService.sessionCheck(id, sessionUser)) {
            log.debug("질문 수정 전송 검증: 실패(로그아웃 되었습니다!!!)");
            return "error_delete";
        }

        article.setId(id);
        articleService.update(article);
        log.debug("게시글 수정 성공");
        return "redirect:/";
    }

    // 게시글 삭제 DELETE (서비스)
    @DeleteMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable long id, HttpSession session, Model model) {
        // 로그인 유저 = 게시글 작성자 검증
        User sessionUser = (User) sessionUtil.getUserInfo(session);
        if (!articleService.sessionCheck(id, sessionUser)) {
            log.debug("게시글 삭제: 실패(본인이 아님!!!! 떽!!!)");
            return "error";
        }

        // 댓글 존재 여부 검증
        List<Reply> replyList = replyService.findAllReply(id);
        if (!replyList.isEmpty()) {
            log.debug("게시글 삭제: 실패(댓글이 존재합니다.)");
            model.addAttribute("errorMessage", "실패(댓글이 존재합니다.)");
            return "error_delete";
        }

        articleService.delete(id);
        log.debug("게시글 삭제: 성공");
        return "redirect:/";
    }
}
