package kr.codesqaud.cafe.exception.userException;

public class UserSessionExpireException extends UserException{
    @Override
    public String getMessage() {
        return "섹션 값이 만료되었습니다.";
    }
}
