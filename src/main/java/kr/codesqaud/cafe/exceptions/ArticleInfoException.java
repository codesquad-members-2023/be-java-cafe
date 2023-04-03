package kr.codesqaud.cafe.exceptions;

public class ArticleInfoException extends Exception {
    private final int errorCode;
    public static final int INVALID_REPLY_CODE = 4;
    public static final int REQUIRE_ON_COMMENT_DELETE_CODE = 3;
    public static final int INVALID_ARTICLE_CODE = 2;
    public static final int WRITER_NOT_MATCHING_CODE = 1;
    public static final int DEFAULT = 0;
    public static final String UNAUTHORIZED_MODIFICATION_MESSAGE = "자신의 글만 수정할 수 있습니다.";
    public static final String UNAUTHORIZED_DELETE_MESSAGE = "자신의 글만 삭제할 수 있습니다.";
    public static final String INVALID_ARTICLE_MESSAGE = "존재하지 않는 글입니다.";
    public static final String INVALID_REPLY_MESSAGE = "존재하지 않는 댓글입니다.";
    public static final String REQUIRE_ON_COMMENT_DELETE_MESSAGE = "댓글이 달린 글은 삭제할 수 없습니다.";

    public ArticleInfoException() {
        errorCode = DEFAULT;
    }

    public ArticleInfoException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
