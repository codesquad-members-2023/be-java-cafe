package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.UserUpdateForm;
import kr.codesqaud.cafe.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateFormValidator implements Validator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final int MAX_LENGTH = 30;

    private final UserRepository userRepository;

    @Autowired
    public UserUpdateFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateForm userUpdateForm = (UserUpdateForm) target;

        if (!StringUtils.hasText(userUpdateForm.getNewPassword())) {
            errors.rejectValue("newPassword", "required.userUpdateForm.newPassword", "새 패스워드를 입력해주세요.");
        }
        if (!StringUtils.hasText(userUpdateForm.getName())) {
            errors.rejectValue("name", "required.userUpdateForm.name", "이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(userUpdateForm.getEmail())) {
            errors.rejectValue("email", "required.userUpdateForm.email", "이메일을 입력해주세요.");
        }

        if (userUpdateForm.getNewPassword().length() >= MAX_LENGTH) {
            errors.rejectValue("newPassword", "over.userUpdateForm.newPassword", "패스워드는 30자 미만으로 작성해주세요.");
        }
        if (userUpdateForm.getName().length() >= MAX_LENGTH) {
            errors.rejectValue("name", "over.userUpdateForm.name", "이름은 30자 미만으로 작성해주세요.");
        }

        if (!userUpdateForm.getNewPassword().matches(".*[!@#$%^&*].*")) {
            errors.rejectValue("newPassword", "invalid.userUpdateForm.newPassword", "비밀번호는 특수문자중 하나 이상을 포함해야 합니다.");
        }
        if (!userUpdateForm.getEmail().matches("[^@]+@[^@]+\\.[^@]+")) {
            errors.rejectValue("email", "invalid.userUpdateForm.email", "유효하지 않은 이메일입니다.");
        }

        User existUser = userRepository.findById(userUpdateForm.getId());
        if (!existUser.getPassword().equals(userUpdateForm.getOldPassword())) {
            errors.rejectValue("oldPassword", "wrong.userUpdateForm.oldPassword", "기존 패스워드를 잘못 입력하셨습니다.");
        }
    }
}
