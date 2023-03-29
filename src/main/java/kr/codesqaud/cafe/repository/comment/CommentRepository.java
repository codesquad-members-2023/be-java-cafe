package kr.codesqaud.cafe.repository.comment;

import kr.codesqaud.cafe.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository {

    void save(Comment comment);

    Comment findByCommentId(long commentId);

    List<Comment> findAllCommentsByArticleId(long articleId);

    int findNumOfCommentsByArticleId(long articleId);

    void deleteComment(long commentId, LocalDateTime now);
}
