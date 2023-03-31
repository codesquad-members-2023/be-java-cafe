package kr.codesqaud.cafe.validation.user;

import kr.codesqaud.cafe.dto.user.UserLoginDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserLoginValidateTest {

    private final UserLoginValidator validator;
    private UserTestErrorConstant testUserConstant = new UserTestErrorConstant();
    private UserTestInformationConstant userInformation = new UserTestInformationConstant();


    @Autowired
    public UserLoginValidateTest(UserLoginValidator validator) {
        this.validator = validator;
    }

    @Test
    @DisplayName("로그인 아이디 빈칸 검증")
    void blankUserId() {
        UserLoginDTO user = new UserLoginDTO(userInformation.BLANK, userInformation.EXISTING_MEMBER_PASSWORD);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.REQUIRED_USERID);
    }

    @Test
    @DisplayName("로그인 비밀번호 빈칸 검증")
    void blankPassword() {
        UserLoginDTO user = new UserLoginDTO(userInformation.EXISTING_MEMBER_ID, userInformation.BLANK);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCodes()).contains(testUserConstant.REQUIRED_PASSWORD);
    }

    @Test
    @DisplayName("로그인 잘못된 아이디 검증")
    void wrongId() {
        UserLoginDTO user = new UserLoginDTO(userInformation.WRONG_USERID, userInformation.EXISTING_MEMBER_PASSWORD);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.WRONG_USERID);
    }

    @Test
    @DisplayName("로그인 잘못된 비밀번호 검증")
    void wrongPassword() {
        UserLoginDTO user = new UserLoginDTO(userInformation.EXISTING_MEMBER_ID, userInformation.WRONG_PASSWORD);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);

        FieldError fieldError = errors.getFieldError();

        assertThat(fieldError.getCode()).isEqualTo(testUserConstant.WRONG_PASSWORD);
    }
}
