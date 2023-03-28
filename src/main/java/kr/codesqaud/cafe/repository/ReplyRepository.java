package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.ReplyDto;

public interface ReplyRepository {
    public void addReply(Reply reply);
    public List<ReplyDto> getReplyList();

    public void deleteReply(long articleId, long replyId);
}
