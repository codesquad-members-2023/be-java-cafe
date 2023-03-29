package kr.codesqaud.cafe.exception;

public enum ExceptionStatus {
    NO_SESSION_USER("로그인이 필요한 기능입니다."),
    INVALID_MEMBER("로그인한 회원이 아닙니다."),
    INVALID_WRITER("작성자만 수정/삭제 할 수 있습니다."),

    UPDATE_FAILED_WRONG_PASSWORD("비밀번호가 다릅니다."),
    MEMBER_NOT_FOUND("회원 정보를 다시 입력해주세요."),
    DUPLICATE_MEMBER_INFO("중복된 아이디가 있습니다."),
    LOGIN_FAILED("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ExceptionStatus(String message) {
        this.message = message;
    }
}
