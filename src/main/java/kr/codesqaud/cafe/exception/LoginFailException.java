package kr.codesqaud.cafe.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException() {
    }

    public LoginFailException(String s) {
        super(s);
    }
}
