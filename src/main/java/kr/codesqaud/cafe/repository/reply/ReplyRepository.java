package kr.codesqaud.cafe.repository.reply;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.dto.reply.ReplySaveDTO;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    void save(ReplySaveDTO reply);

    List<Reply> findAllReplyByArticleId(int articleId);

    Optional<Reply> findReplyByReplyId(int replyId);

    void delete(int replyId);
}
