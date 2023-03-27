package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyControl {
    private final ReplyService replyService;

    @Autowired
    public ReplyControl(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 댓글 작성
    @PostMapping("/articles/{articleId}/answers/{userId}")
    public String writeReply(HttpSession session, @PathVariable long articleId, @PathVariable String userId, @ModelAttribute Reply reply) {
        reply.setArticleId(articleId);
        reply.setWriter(userId);
        replyService.write(reply);
        return "redirect:/articles/{articleId}";
    }
}
