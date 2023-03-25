package kr.codesqaud.cafe.validation.user;

public class TestUserConstant {

    public final String USERID_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA";
    public final String PASSWORD_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA";
    public final String NAME_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA";
    public final String EMAIL_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    public final String REQUIRED_USERID = "required.user.userId";
    public final String REQUIRED_PASSWORD = "required.user.password";
    public final String REQUIRED_NAME = "required.user.name";
    public final String REQUIRED_EMAIL = "required.user.email";
    public final String DUPLICATED_USERID = "error.user.duplicatedId";
    public final String USERID_LENGTH_ERROR = "error.user.userIdLength";
    public final String PASSWORD_LENGTH_ERROR = "error.user.passwordLength";
    public final String NAME_LENGTH_ERROR = "error.user.nameLength";
    public final String EMAIL_LENGTH_ERROR = "error.user.emailLength";
    public final String WRONG_USERID = "error.user.userId";
    public final String WRONG_PASSWORD = "error.user.password";
    public final String PASSWORD_FORMAT_ERROR ="error.user.passwordFormat";

}
