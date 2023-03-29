package kr.codesqaud.cafe.repository.comment;

import kr.codesqaud.cafe.domain.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> findAllCommentsByArticleId(long articleId);
}
