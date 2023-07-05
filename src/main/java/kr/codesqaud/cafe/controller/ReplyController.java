package kr.codesqaud.cafe.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.repository.ReplyDto;
import kr.codesqaud.cafe.repository.ResultDto;
import kr.codesqaud.cafe.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/article/{articleId}/replyAll")
    public List<ReplyDto> findArticleALLById(@PathVariable("articleId") int articleId) {
        return replyService.findReplyAllByArticleId(articleId);
    }

    @PostMapping("/article/{articleId}/reply/creat")
    public ReplyDto findArticleById(Reply reply) {
        replyService.createReply(reply);
        return replyService.findReplyByReplyId(String.valueOf(reply.getReplyId()));
    }

    @DeleteMapping("/article/{articleId}/reply/{replyId}/delete")
    public ResultDto deleteReply(@PathVariable("articleId") String articleId,
                                 @PathVariable("replyId") String replyId, HttpSession session, ResultDto resultDto) {
        ReplyDto replyDto = replyService.findReplyByReplyId(replyId);
        if (!replyDto.getUserId().equals(session.getAttribute("userId"))) {
            resultDto.setMessage("다른 사람의 댓글은 삭제할 수 없습니다.");
            return resultDto;
        }
        replyService.deleteReply(replyDto);
        resultDto.setValid(true);
        return resultDto;
    }
}
