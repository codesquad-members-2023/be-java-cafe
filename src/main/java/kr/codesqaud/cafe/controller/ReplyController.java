package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.config.ConstantConfig;
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
}
