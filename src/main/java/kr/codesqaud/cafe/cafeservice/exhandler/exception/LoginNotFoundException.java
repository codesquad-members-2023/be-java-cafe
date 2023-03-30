package kr.codesqaud.cafe.cafeservice.exhandler.exception;

public class LoginNotFoundException extends RuntimeException {

    public LoginNotFoundException(String message) {
        super(message);
    }
}
