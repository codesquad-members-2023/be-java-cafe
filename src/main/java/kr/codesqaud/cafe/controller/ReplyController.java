package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.basic.User;
import kr.codesqaud.cafe.config.ConstConfig;
import kr.codesqaud.cafe.repository.DataBaseRepository.DatabaseReplyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        User User = (User)session.getAttribute(ConstConfig.SESSION_ID);
        replyRepository.save(new Reply(articleId, User.getUserId(), content));
        return "redirect:/qna/show/" + articleId;
    }

    @DeleteMapping("/delete/{replyId}")
    public String delete(@PathVariable int replyId,
                         HttpSession session) {
        User user = (User)session.getAttribute(ConstConfig.SESSION_ID);
        Reply reply = replyRepository.findByReplyId(replyId);
        if (user.getUserId().equals(reply.getWriter())) {
            replyRepository.delete(reply);
        }
        return "redirect:/qna/show/" + reply.getArticleId();
    }

}
