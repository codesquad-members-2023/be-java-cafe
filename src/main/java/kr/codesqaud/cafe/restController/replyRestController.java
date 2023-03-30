package kr.codesqaud.cafe.restController;

import kr.codesqaud.cafe.basic.Reply;
import kr.codesqaud.cafe.basic.ReplyDTO;
import kr.codesqaud.cafe.config.ConstantConfig;
import kr.codesqaud.cafe.repository.DataBaseRepository.DatabaseReplyRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class replyRestController {

    DatabaseReplyRepository replyRepository;
    public replyRestController(DatabaseReplyRepository databaseReplyRepository) {
        this.replyRepository = databaseReplyRepository;
    }

    @GetMapping("/reply/{articleId}")
    public List<ReplyDTO> show(@PathVariable int articleId) {
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        replyRepository.findByArticleId(articleId).stream()
                .forEach(reply -> replyDTOS.add(new ReplyDTO(reply)));
        return replyDTOS;
    }

    @PostMapping("/reply")
    public List<ReplyDTO> create(@RequestBody Reply reply,
                         HttpSession session) {
        int articleId = reply.getArticleId();
        String content = reply.getContent();
        String UserId = (String)session.getAttribute(ConstantConfig.SESSION_ID);
        replyRepository.save(new Reply(articleId, UserId, content));
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        replyRepository.findByArticleId(articleId).stream()
                .forEach(r -> replyDTOS.add(new ReplyDTO(r)));
        return replyDTOS;
    }

}
