package kr.codesqaud.cafe.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.ReplyRepository;
import kr.codesqaud.cafe.utils.SessionUtils;

@RestController
public class ReplyAjaxController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @PostMapping("/qna/{articleId}/reply")
    public Reply create(@PathVariable Long articleId, @RequestParam String replyContents, HttpSession session) {

        String userId = SessionUtils.getSessionId(session);
        //TODO: 인터셉터 되는지 확인
        System.out.println(replyContents);


        Reply reply = new Reply(userId, replyContents, LocalDateTime.now(), articleId);
        replyRepository.addReply(reply);

        return reply;
    }
}
