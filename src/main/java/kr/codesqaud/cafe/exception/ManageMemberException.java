package kr.codesqaud.cafe.exception;

public class ManageMemberException extends RuntimeException {

    public static final String UPDATE_FAILED_WRONG_PASSWORD = "비밀번호가 다릅니다.";
    public static final String MEMBER_NOT_FOUND = "회원 정보를 다시 입력해주세요.";
    public static final String DUPLICATE_MEMBER_INFO = "중복된 아이디가 있습니다.";
    public static final String LOGIN_FAILED = "아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.";

    public ManageMemberException(String message) {
        super(message);
    }
}
