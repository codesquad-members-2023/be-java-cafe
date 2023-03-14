package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class UserJoinRequestDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String phone;

    public User toEntity() {
        if (password!=passwordConfirm) return null;

        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .localDate(LocalDate.now())
                .build();
    }
}
