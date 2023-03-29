package kr.codesqaud.cafe.repository;

import java.util.List;

import kr.codesqaud.cafe.exceptions.ArticleInfoException;
import kr.codesqaud.cafe.model.Reply;
import kr.codesqaud.cafe.model.ReplyDto;

public interface ReplyRepository {
    public void addReply(Reply reply);
    public List<ReplyDto> getReplyList(long articleId);

    public void deleteReply(long articleId, long replyId);

    public boolean validateDelete(long articleId, String userId);

    public ReplyDto findById(long replyId, long articleId) throws ArticleInfoException;
}
