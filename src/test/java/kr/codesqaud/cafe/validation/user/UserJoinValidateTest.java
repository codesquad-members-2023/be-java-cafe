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
    private TestUserConstant testUserConstant = new TestUserConstant();

    @Autowired
    public UserJoinValidateTest(UserJoinValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("userId가 존재하는 경우 검증")
    void duplicatedUserId() {
        String duplicatedUser = "first";
        User user = new User(duplicatedUser, "password!", "name", "email@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.DUPLICATED_USERID);
    }

    @Test
    @DisplayName("회원가입 아이디 빈칸 검증")
    void blankUserId() {
        User user = new User("", "password!", "name", "email@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_USERID);
    }

    @Test
    @DisplayName("비밀번호 빈칸 검증")
    void blankPassword() {
        User user = new User("userID", "", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("회원가입 이름 빈칸 검증")
    void blankName() {
        User user = new User("first", "password!", "", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_NAME);
    }

    @Test
    @DisplayName("회원가입 이메일 빈칸 검증")
    void blankEmail() {
        User user = new User("first", "password!", "name", "");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_EMAIL);
    }

    @Test
    @DisplayName("회원가입 아이디 길이제한")
    void limitUserIdLength() {
        User user = new User(testUserConstant.NAME_LENGTH_OVER, "password!", "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.USERID_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 비밀번호 길이제한")
    void limitPasswordLength() {
        User user = new User("userId", testUserConstant.PASSWORD_LENGTH_OVER, "name", "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.PASSWORD_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 이름 길이제한")
    void limitNameLength() {
        User user = new User("userId", "password!", testUserConstant.NAME_LENGTH_OVER, "email@com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.NAME_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 아이디 길이제한")
    void limitEmailError() {
        User user = new User("userId", "password!", "name", testUserConstant.EMAIL_LENGTH_OVER);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.EMAIL_LENGTH_ERROR);
    }

    @Test
    @DisplayName("회원가입 비밀번호 특수문자 포함 확인")
    void specialChar() {
        User user = new User("userId", "password", "name", "email@email");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.PASSWORD_FORMAT_ERROR);
    }

    @Test
    @DisplayName("회원가입 이메일 형식 틀리면 에러")
    void emailFormatTest() {
        User user = new User("userId", "password!", "name", "email");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.EMAIL_FORMAT_ERROR);
    }

    @Test
    @DisplayName("회원가입 이메일 형식 올바르게 입력하면 예외 x")
    void emailFormatTest2() {
        User user = new User("userId", "password!", "name", "email@email.com");
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        FieldError fieldError = errors.getFieldError();

        assertThatThrownBy(() -> fieldError.getCode()).isInstanceOf(NullPointerException.class);
    }
}
