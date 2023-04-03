package kr.codesqaud.cafe.cafeservice.controller;


import kr.codesqaud.cafe.cafeservice.domain.Reply;
import kr.codesqaud.cafe.cafeservice.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.cafeservice.service.ArticleService;
import kr.codesqaud.cafe.cafeservice.service.ReplyService;
import kr.codesqaud.cafe.cafeservice.session.LoginSessionUtils;
import kr.codesqaud.cafe.cafeservice.session.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class ReplyController {

    private final ReplyRepository repository;
    private final ArticleService articleService;
    private final ReplyService service;
    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    public ReplyController(ReplyRepository repository, ArticleService articleService, ReplyService service) {
        this.repository = repository;
        this.articleService = articleService;
        this.service = service;
    }

    @DeleteMapping("/articles/{articleId}/reply/{id}")
    public String deleteReply(@PathVariable Long articleId, @PathVariable Long id, HttpSession session) {

        Reply reply = repository.findByWithArticle(articleId, id)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 댓글이 없습니다.."));

        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (sessionUtils.getId() == reply.getMember_id()) {
            repository.delete(id);
            System.out.println(" 삭제");
            return "redirect:/articles/{articleId}";
        }
        throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
    }

    @PostMapping("/articles/{questionId}/reply")
    public String saveReply(@PathVariable Long questionId, @RequestParam String content, HttpSession session) {
        //Todo 로그인 한 사용자의 id, 지금 보고 있는 article의 id

        LoginSessionUtils sessionUtils = (LoginSessionUtils) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Reply reply = new Reply(sessionUtils.getId(), questionId, content, sessionUtils.getNickName());

        repository.save(reply);
        return "redirect:/articles/{questionId}";
    }
}
