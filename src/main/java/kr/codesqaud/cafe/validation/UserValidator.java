package kr.codesqaud.cafe.validation;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserValidator implements Validator {

    private final MemberRepository memberRepository;

    @Autowired
    public UserValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User loginUser = (User) target;
        // 빈 비밀번호, 아이디 확인
        if (!StringUtils.hasText(loginUser.getUserId())) {
            errors.rejectValue("userId", "required.user.userId");
        }
        if (!StringUtils.hasText(loginUser.getPassword())) {
            errors.rejectValue("password", "required.user.password");
        }

        if (StringUtils.hasText(loginUser.getUserId())) {
            // 잘못된 아이디 확인
            List<User> all = memberRepository.findAll();
            for (User user : all) {
                if (user.getUserId().equals(loginUser.getUserId())) {
                    User originUer = memberRepository.findById(loginUser.getUserId());
                    // 잘못된 비밀번호 확인
                    if (!originUer.getPassword().equals(loginUser.getPassword())) {
                        errors.rejectValue("password", "error.user.password");
                    }
                    return;
                }
            }
            // 위에서 검증되지 않았다면 아이디 에러
            errors.rejectValue("userId", "error.user.userId");
        }
    }
}
