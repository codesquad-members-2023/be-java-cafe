package kr.codesqaud.cafe.validation.user;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserJoinValidateTest {

    private final UserJoinValidator validator;
    private UserTestErrorConstant errorCode = new UserTestErrorConstant();
    private UserTestInformationConstant userInformation = new UserTestInformationConstant();

    @Autowired
    public UserJoinValidateTest(UserJoinValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("userId가 존재하는 경우 검증")
    void duplicatedUserId() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.DUPLICATED_USERID);
    }

    @Test
    @DisplayName("회원가입 아이디 빈칸 검증")
    void blankUserId() {
        User user = new User(userInformation.BLANK, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.REQUIRED_USERID);
    }

    @Test
    @DisplayName("비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.BLANK, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원가입 이름 빈칸 검증")
    void blankName() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.BLANK, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원가입 이메일 빈칸 검증")
    void blankEmail() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_NAME, userInformation.BLANK);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.REQUIRED_EMAIL);
    }

    @Test
    @DisplayName("회원가입 아이디 길이제한")
    void limitUserIdLength() {
        User user = new User(userInformation.USERID_LENGTH_OVER, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.USERID_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 비밀번호 길이제한")
    void limitPasswordLength() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.PASSWORD_LENGTH_OVER, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.PASSWORD_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 이름 길이제한")
    void limitNameLength() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.NAME_LENGTH_OVER, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.NAME_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 이메일 길이제한")
    void limitEmailError() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.EMAIL_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.EMAIL_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 비밀번호 특수문자 포함 확인")
    void specialChar() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.WRONG_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.PASSWORD_FORMAT_ERROR);
    }

    @Test
    @DisplayName("회원가입 이메일 형식 틀리면 에러")
    void emailFormatTest() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.WRONG_EMAIL_FORMAT);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(errorCode.EMAIL_FORMAT_ERROR);
    }

    @Test
    @DisplayName("회원가입 이메일 형식 올바르게 입력하면 예외 x")
    void emailFormatTest2() {
        User user = new User(userInformation.NEW_MEMBER_ID, userInformation.NEW_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        FieldError fieldError = errors.getFieldError();

        assertThatThrownBy(() -> fieldError.getCode()).isInstanceOf(NullPointerException.class);
    }
}
