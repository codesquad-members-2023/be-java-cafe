package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.reply.ReplySaveDTO;
import kr.codesqaud.cafe.repository.reply.ReplyRepository;
import kr.codesqaud.cafe.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class ApiAnswerController {

    private final ReplyRepository replyRepository;

    @Autowired
    public ApiAnswerController(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @PostMapping("/qna/show/{articleId}")
    public ReplySaveDTO createAnswer(@ModelAttribute Reply reply, HttpSession session) {
        User loginUser = SessionUtil.getSessionedUser(session);
        ReplySaveDTO replySaveDTO = new ReplySaveDTO(reply.getArticleId(), loginUser.getUserId(), reply.getContents(), Timestamp.valueOf(LocalDateTime.now()));

        replyRepository.save(replySaveDTO);

        return replySaveDTO;
    }
}
