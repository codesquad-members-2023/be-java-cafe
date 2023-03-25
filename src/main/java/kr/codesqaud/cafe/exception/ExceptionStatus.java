package kr.codesqaud.cafe.exception;

public enum ExceptionStatus {
    WRONG_PASSWORD("비밀번호가 다릅니다."),
    INVALID_MEMBER("회원 정보를 다시 입력해주세요."),
    NO_SESSION_USER("로그인이 먼저 필요합니다."),
    DIFFERENT_MEMBER("로그인한 유저가 아닙니다."),
    DUPLICATE_MEMBER_INFO("중복된 아이디가 있습니다."),
    LOGIN_FAIL("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ExceptionStatus(String message) {
        this.message = message;
    }
}
