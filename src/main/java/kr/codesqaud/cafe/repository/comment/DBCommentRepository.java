package kr.codesqaud.cafe.repository.comment;

import kr.codesqaud.cafe.domain.Comment;

import java.util.List;

public class DBCommentRepository implements CommentRepository {

    @Override
    public List<Comment> findAllCommentsByArticleId(long articleId) {
        return null;
    }
}
