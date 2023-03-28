package kr.codesqaud.cafe.exception;

public class ManageMemberException extends RuntimeException {

    private ExceptionStatus status;

    public ExceptionStatus getStatus() {
        return status;
    }

    public ManageMemberException(ExceptionStatus errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode;
    }
}
