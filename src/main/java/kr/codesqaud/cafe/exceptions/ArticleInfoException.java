package kr.codesqaud.cafe.exceptions;

public class ArticleInfoException extends RuntimeException {
    private final int errorCode;
    public static final int WRITER_NOT_MATCHING_CODE = 1;
    public static final int DEFAULT = 0;
    public static final String WRONG_NOT_MATCHING_MESSAGE = "자신의 글만 수정할 수 있습니다.";

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
