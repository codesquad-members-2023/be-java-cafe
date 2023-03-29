package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.reply.JdbcReplyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    Logger logger = LoggerFactory.getLogger(getClass());
    private JdbcReplyRepository jdbcReplyRepository;

    public ReplyController(JdbcReplyRepository jdbcReplyRepository) {
        this.jdbcReplyRepository = jdbcReplyRepository;
    }

    @PostMapping("/articles/{articleId}/reply")
    public String save(@ModelAttribute Reply reply, @PathVariable Long articleId, @SessionAttribute(name = MemberSessionUser.LOGIN_MEMBER, required = false) Member member) {
        logger.debug("save {} = ", articleId);
        if (member == null) {
            return "redirect:/login";
        }
        reply.setArticleId(articleId);
        reply.setReplyWriter(member.getUserId());
        jdbcReplyRepository.save(reply);
        return "redirect:/articles/{articleId}";
    }
