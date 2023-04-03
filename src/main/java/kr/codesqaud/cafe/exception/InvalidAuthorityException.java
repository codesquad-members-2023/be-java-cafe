package kr.codesqaud.cafe.exception;

public class InvalidAuthorityException extends Exception {
    public static final String NO_SESSION_USER = "로그인이 필요한 기능입니다.";
    public static final String INVALID_MEMBER = "로그인한 유저가 아닙니다.";

    public InvalidAuthorityException(String status) {
        super(status);
    }
}
