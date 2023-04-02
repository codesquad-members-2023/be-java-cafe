package kr.codesqaud.cafe.exception;

public class ManageArticleException extends Exception{
    public static final String NOT_POSSIBLE_DELETE = "댓글이 있는 게시글은 삭제할 수 없습니다.";
    public static final String INVALID_WRITER = "글 작성자만 수정/삭제할 수 있습니다.";

    public ManageArticleException(String message) {
        super(message);
    }
}
