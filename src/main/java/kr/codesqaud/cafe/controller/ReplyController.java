package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
