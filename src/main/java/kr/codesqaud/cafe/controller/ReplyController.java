package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.SessionConstant;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.H2DBReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    private final H2DBReplyRepository replyRepository;

    @Autowired
    public ReplyController(H2DBReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @PostMapping("/articles/{index}/replies")
    public String reply(@PathVariable int index, @RequestParam String contents, HttpSession session) {
        Reply reply = new Reply(contents, (int) session.getAttribute(SessionConstant.LOGIN_USER_ID), index);

        replyRepository.save(reply);

        return "redirect:/articles/{index}";
    }
}
