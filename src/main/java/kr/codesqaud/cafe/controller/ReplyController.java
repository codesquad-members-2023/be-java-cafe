package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstantConfig;
import kr.codesqaud.cafe.exception.gobalExeption.NotFoundException;
import kr.codesqaud.cafe.repository.DataBaseRepository.DatabaseReplyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    DatabaseReplyRepository replyRepository;

    public ReplyController(DatabaseReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @PostMapping("/create/{articleId}")
    public String create(@PathVariable int articleId,
                         @RequestParam String content,
                         HttpSession session) {
        String UserId = (String)session.getAttribute(ConstantConfig.SESSION_ID);
        replyRepository.save(new Reply(articleId, UserId, content));
        return "redirect:/qna/show/" + articleId;
    }

    @DeleteMapping("/delete/{replyId}")
    public String delete(@PathVariable int replyId,
                         HttpSession session) {
        String userId = (String)session.getAttribute(ConstantConfig.SESSION_ID);

        // TODO : 코드가 길어지는 감이 있다. 개선할 필요성이 느껴진다.
        // 원하는 로직은 댓글을 삭제하는 것이고, 이미 삭제되어 있는데, 굳이 에러메세지를 보내줄 필요성이 있을까?
        // 한번 더 생각해보고, 예외처리를 없애주는 절차를 밟아보자
        Optional<Reply> replyOptional = replyRepository.findByReplyId(replyId);
        if (replyOptional.isEmpty()) throw new NotFoundException("이미 댓글이 삭제되었습니다.");
        Reply reply = replyOptional.get();

        // TODO : 객체지향적이지 않는 코드인 것 같다. 검증하는 부분을 공부 후, 개선해보자
        if (userId.equals(reply.getWriter())) {
            replyRepository.delete(reply);
        }
        return "redirect:/qna/show/" + reply.getArticleId();
    }

}
