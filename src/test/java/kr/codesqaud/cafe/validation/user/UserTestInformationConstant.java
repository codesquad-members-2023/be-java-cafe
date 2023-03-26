package kr.codesqaud.cafe.validation.user;

public class UserTestInformationConstant {

    public final String EXISTING_MEMBER_ID = "first";
    public final String EXISTING_MEMBER_PASSWORD = "password!";
    public final String EXISTING_MEMBER_NAME = "first-name";
    public final String EXISTING_MEMBER_EMAIL = "first@email.com";

    public final String NEW_MEMBER_ID = "userId";
    public final String NEW_MEMBER_PASSWORD = "password!";
    public final String NEW_MEMBER_NAME = "newUser";
    public final String NEW_MEMBER_EMAIL = "newEmail@eamil.com";

    public final String USERID_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA";
    public final String PASSWORD_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA!";
    public final String NAME_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAA";
    public final String EMAIL_LENGTH_OVER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA@eamil.com";

    public final String BLANK = "";
    public final String WRONG_PASSWORD = "wrongPassword";
    public final String WRONG_EMAIL_FORMAT = "email@";

    public final String WRONG_USERID = "wrongId";

}
