package kr.codesqaud.cafe.exceptions;

public class ArticleInfoException extends Exception {
    private final int errorCode;
    public static final int INVALID_ARTICLE_CODE = 2;
    public static final int WRITER_NOT_MATCHING_CODE = 1;
    public static final int DEFAULT = 0;
    public static final String UNAUTHORIZED_MODIFICATION_MESSAGE = "자신의 글만 수정할 수 있습니다.";
    public static final String UNAUTHORIZED_DELETE_MESSAGE = "자신의 글만 삭제할 수 있습니다.";
    public static final String INVALID_ARTICLE_MESSAGE = "존재하지 않는 글입니다.";

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
