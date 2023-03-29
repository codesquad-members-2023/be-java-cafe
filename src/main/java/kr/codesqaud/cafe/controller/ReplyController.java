package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import kr.codesqaud.cafe.repository.H2DBReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final H2DBReplyRepository replyRepository;

    @Autowired
    public ReplyController(H2DBReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @PostMapping("/articles/{index}/replies")
    @ResponseBody
    public ReplyWithUser apiTest(@PathVariable int index, @RequestParam String contents, HttpSession session) {
        Reply reply = new Reply(contents, (int) session.getAttribute(SessionConstant.LOGIN_USER_ID), index);

        int replyId = replyRepository.save(reply);

        return new ReplyWithUser(replyId, reply.getUserId(), (String) session.getAttribute(SessionConstant.LOGIN_USER_NICKNAME),
                contents, reply.getCreateDate());
    }

    @DeleteMapping("/articles/{articleId}/replies/{replyId}")
    public String delete(@PathVariable int articleId, @PathVariable int replyId, HttpSession session) {
        Reply reply = replyRepository.findById(replyId);
        int userId = (int) session.getAttribute(SessionConstant.LOGIN_USER_ID);

        if (userId != reply.getUserId()) {
            throw new IllegalArgumentException("[ERROR] 자신이 작성하지 않은 댓글은 삭제할 수 없습니다.");
        }

        replyRepository.delete(replyId);
        return "redirect:/articles/{articleId}";
    }
}
