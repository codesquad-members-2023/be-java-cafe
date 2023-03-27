package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.repository.JdbcTemplateReplyRepository;

import java.util.List;
import java.util.Optional;

public class ReplyService {
    private final JdbcTemplateReplyRepository replyRepository;

    public ReplyService(JdbcTemplateReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글 작성 & 업데이트
     */
    public void write(Reply reply) {
        replyRepository.saveReply(reply);
    }

    public boolean update(Reply reply) {
        return replyRepository.updateReply(reply);
    }

    /**
     * 댓글 삭제
     */
    public boolean delete(long id) {
        return replyRepository.deleteReply(id);
    }

    /**
     * 댓글 조회
     */
    public List<Reply> findAllReply(long articleId) {
        return replyRepository.findAllReply(articleId);
    }

    public Optional<Reply> findByReplyId(long id) {
        return replyRepository.findById(id);
    }
}
