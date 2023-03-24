package kr.codesqaud.cafe.exception.userException;

public class UserLoginException extends UserException{
    @Override
    public String getMessage() {
        return "잘못된 아이디나 비밀번호입니다.";
    }
}
