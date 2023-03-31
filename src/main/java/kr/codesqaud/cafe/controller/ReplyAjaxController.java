package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.exceptions.ArticleInfoException.*;
import static kr.codesqaud.cafe.utils.SessionUtils.*;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.ReplyAjaxDto;
import kr.codesqaud.cafe.model.Result;
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
    public ReplyAjaxDto create(@PathVariable Long articleId, @RequestParam String replyContents, HttpSession session) {
        String userId = SessionUtils.getSessionId(session);
        //TODO: 인터셉터 되는지 확인

        Reply reply = new Reply(userId, replyContents, LocalDateTime.now(), articleId);
        long replyId = replyRepository.addReply(reply);
        ReplyAjaxDto replyAjaxDto = new ReplyAjaxDto(replyId, userId, replyContents, LocalDateTime.now(), articleId);

        return replyAjaxDto;
    }

    @DeleteMapping("/qna/{articleId}/reply/{replyId}")
    public ResponseEntity<Result> delete(@PathVariable Long articleId, @PathVariable Long replyId, HttpSession session) throws
            ArticleInfoException {
        String replyWriter = replyRepository.findById(replyId, articleId).getWriter();
        //Session의 사용자와 댓글 작성자가 동일해야한다.
        if (!getSessionId(session).equals(replyWriter)) {
            return new ResponseEntity<>(new Result(UNAUTHORIZED_DELETE_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        replyRepository.deleteReply(articleId, replyId);

        return new ResponseEntity<>(new Result(), HttpStatus.OK);
    }
}
