package kr.codesqaud.cafe.repository.comment;

import kr.codesqaud.cafe.domain.Comment;

import java.util.List;

public interface CommentRepository {

    void save(Comment comment);

    List<Comment> findAllCommentsByArticleId(long articleId);

    int findNumOfCommentsByArticleId(long articleId);
}
