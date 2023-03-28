package kr.codesqaud.cafe.service;

import java.util.List;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.repository.ReplyDao;
import kr.codesqaud.cafe.repository.ReplyDto;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyDao replyDao;
    private static int replyId = 2;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }

    public static int getReplySize() {
        return ++replyId;
    }

    public void createReply(Reply reply) {
        replyDao.createReply(reply);
    }

    public List<ReplyDto> findReplyAllByArticleId(int articleId) {
        return replyDao.findReplyAllByArticleId(articleId);
    }

    public ReplyDto findReplyByReplyId(String replyId) {
        return replyDao.findReplyByReplyId(Integer.parseInt(replyId));
    }

    public void updateReply(Reply reply, String oldReplyId) {
        replyDao.updateReply(reply, oldReplyId);
    }

    public void deleteReply(ReplyDto replyDto) {
        replyDao.deleteReply(replyDto);
    }
}
