package kr.codesqaud.cafe.cafeservice.validator;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "member", "required");
        if (member.getUserName() == null) {
            errors.rejectValue("userName", "required.userName");
        }
        if (member.getEmail() == null) {
            errors.rejectValue("email", "required.email");
        }
        if (member.getPassword() == null) {
            errors.rejectValue("password", "required.password");
        }
    }
}
