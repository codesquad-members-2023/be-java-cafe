package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Reply;
import kr.codesqaud.cafe.domain.article.Writer;
import kr.codesqaud.cafe.dto.ReplyResponse;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.H2ArticleRepository;
import kr.codesqaud.cafe.util.SessionUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@RestController
@RequestMapping("/api/articles/{articleId}/answers")
public class ApiAnswerController {
    private ArticleRepository articleRepository;

    public ApiAnswerController(DataSource dataSource) {
        this.articleRepository = new H2ArticleRepository(dataSource);
    }

    @PostMapping("")
    public ReplyResponse create(@PathVariable Long articleId, String contents, HttpSession session) {
        SessionUser sessionUser = SessionUser.getSessionUser(session);

        Reply reply = new Reply(articleId, new Writer(sessionUser.getId(), sessionUser.getNickName()), contents);
        long key = articleRepository.saveReply(reply);
        ReplyResponse replyById = articleRepository.findReplyById(key);
        return replyById;
    }
}
