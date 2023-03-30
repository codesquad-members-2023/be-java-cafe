package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcTemplateReplyRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * 댓글 검증
     */
    public boolean sessionCheck(User sessionUser, long id) {
        Reply checkId = findByReplyId(id).orElseThrow(null);
        return checkId != null && checkId.getWriter().equals(sessionUser.getName());
    }

    /**
     * 댓글 조회
     * 1. articleId 에 해당하는 모든 댓글
     * 2. 로그인 유저와 같지 않은 댓글
     */
    @Transactional(readOnly = true)
    public List<Reply> findAllReply(long articleId) {
        return replyRepository.findAllReply(articleId);
    }

    @Transactional(readOnly = true)
    public List<Reply> findAllNonMatchReply(long articleId, String userId) {
        return findAllReply(articleId).stream()
                .filter(reply -> !reply.getWriter().equals(userId))
                .collect(Collectors.toList());
    }

    public Optional<Reply> findByReplyId(long id) {
        return replyRepository.findById(id);
    }
}
