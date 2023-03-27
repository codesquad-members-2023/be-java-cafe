package kr.codesqaud.cafe.cafeservice.exhandler.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message) {
        super(message);
    }
}
