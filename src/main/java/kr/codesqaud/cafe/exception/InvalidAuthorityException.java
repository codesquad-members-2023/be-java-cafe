package kr.codesqaud.cafe.exception;

public class InvalidAuthorityException extends Exception {
    private ExceptionStatus status;

    public ExceptionStatus getStatus() {
        return status;
    }

    public InvalidAuthorityException(ExceptionStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
