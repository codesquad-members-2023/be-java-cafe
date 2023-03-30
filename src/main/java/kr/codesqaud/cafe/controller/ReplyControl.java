package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static kr.codesqaud.cafe.service.SessionUtil.getUserInfo;

@Controller
public class ReplyControl {
    private final ReplyService replyService;

    @Autowired
    public ReplyControl(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 댓글 작성
    @PostMapping("/articles/{articleId}/answers")
    public String writeReply(HttpSession session, @PathVariable long articleId, @ModelAttribute Reply reply) {
        User sessionUser = (User) getUserInfo(session);
        if (sessionUser != null) {
            reply.setArticleId(articleId);
            reply.setWriter(sessionUser.getName());
            replyService.write(reply);
            return "redirect:/articles/{articleId}";
        }
        return "redirect:/login";
    }

    // 댓글 삭제
    @DeleteMapping("/articles/{articleId}/answers/{id}")
    public String deleteReply(HttpSession session, @PathVariable long articleId, @PathVariable long id) {
        User sessionUser = (User) getUserInfo(session);
        if (!replyService.sessionCheck(sessionUser, id)) {
            return "redirect:/error";
        }
        replyService.delete(id);
        return "redirect:/articles/{articleId}";
    }
}
