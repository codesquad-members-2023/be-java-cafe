package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String email;
    private String nickname;
    private String password;
    private static Long sequence = 0L;

    public User toEntity() {
        return User.builder()
                .userId(idIncrease())
                .email(email)
                .nickname(nickname)
                .password(password)
                .createdDate(LocalDate.now())
                .build();
    }

    private static Long idIncrease() {
        return LocalDate.now().hashCode() + ++sequence;
    }
}
