package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final ReplyRepository repository;
    private final SessionUtil sessionUtil;

    @Autowired
    public ReplyController(ReplyRepository repository, SessionUtil sessionUtil) {
        this.repository = repository;
        this.sessionUtil = sessionUtil;
    }


    @PostMapping("/qna/show/{articleId}")
    public String addReply(@ModelAttribute Reply reply, HttpSession session) {
        User user = sessionUtil.getSessionedUser(session);
        reply.setUserId(user.getUserId());

        repository.save(reply);
        return "redirect:/qna/show/" + reply.getArticleId();
    }

    @DeleteMapping("/qna/show/reply/{replyId}")
    public String deleteReply(@PathVariable int replyId, HttpSession session) {
        Reply reply = repository.findReplyByReplyId(replyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        User user = sessionUtil.getSessionedUser(session);
        if (user.getUserId().equals(reply.getUserId())) {
            repository.delete(replyId);
            return "redirect:/qna/show/" + reply.getArticleId();
        }

        throw new IllegalArgumentException("글 작성자만 댓글을 삭제할 수 있습니다.");
    }
}
