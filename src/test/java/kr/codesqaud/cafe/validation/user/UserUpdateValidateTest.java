package kr.codesqaud.cafe.validation.user;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserUpdateValidateTest {

    private final UserUpdateValidator validator;
    private UserTestErrorConstant testUserConstant = new UserTestErrorConstant();
    private UserTestInformationConstant userInformation = new UserTestInformationConstant();

    @Autowired
    public UserUpdateValidateTest(UserUpdateValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("회원정보 수정 비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.BLANK, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCodes()).contains(testUserConstant.REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원정보 수정  이름 빈칸 검증")
    void blankName() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.BLANK, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원정보 수정  이메일 빈칸 검증")
    void blankEmail() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.BLANK);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_EMAIL);
    }

    @Test
    @DisplayName("회원정보 수정 올바른 비밀번호 검증")
    void wrongPassword() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.WRONG_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.WRONG_PASSWORD);
    }

    @Test
    @DisplayName("회원정보 수정 이름 길이 제한")
    void limitNameLength() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.NAME_LENGTH_OVER, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.NAME_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원정보 수정 아이디 길이제한")
    void limitEmailError() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.EMAIL_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.EMAIL_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원정보 수정 이메일 형식 틀리면 에러")
    void emailFormatTest() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.WRONG_EMAIL_FORMAT);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.EMAIL_FORMAT_ERROR);
    }

    @Test
    @DisplayName("회원정보 수정 이메일 형식 올바르게 입력하면 예외 x")
    void emailFormatTest2() {
        User user = new User(userInformation.EXISTING_MEMBER_ID, userInformation.EXISTING_MEMBER_PASSWORD, userInformation.NEW_MEMBER_NAME, userInformation.NEW_MEMBER_EMAIL);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        FieldError fieldError = errors.getFieldError();

        assertThatThrownBy(() -> fieldError.getCode()).isInstanceOf(NullPointerException.class);
    }
}
