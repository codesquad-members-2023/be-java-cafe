package kr.codesqaud.cafe.exceptions;

public class UserInfoException extends Exception {

    private final int errorCode;
    public static final int WRONG_LOGIN_PASSWORD_CODE = 1;
    public static final int WRONG_MODIFICATION_PASSWORD_CODE = 2;
    public static final int ILLEGAL_LOGIN_ACCESS_CODE = 3;
    public static final int ILLEGAL_MODIFICATION_ACCESS_CODE = 4;
    public static final int INVALID_ID_CODE = 5;
    public static final int NON_AUTHORIZED_USER_CODE = 6;
    public static final int DEFAULT = 0;
    public static final String WRONG_PASSWORD_MESSAGE = "잘못된 패스워드입니다.";
    public static final String ILLEGAL_ACCESS_MESSAGE = "잘못된 접근입니다.";
    public static final String INVALID_ID_MESSAGE = "존재하지 않는 ID입니다. 아이디를 확인해주세요.";
    public static final String NON_AUTHORIZED_USER_MESSAGE = "권한이 없습니다. 로그인을 해주세요.";

    public UserInfoException() {
        errorCode = DEFAULT;
    }

    public UserInfoException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
