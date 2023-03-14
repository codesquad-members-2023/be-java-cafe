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
    private String password;
    private String nickname;

    public UserJoinRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .userId(generateUserId())
                .nickname(nickname)
                .password(password)
                .email(email)
                .createdDate(LocalDate.now())
                .build();
    }
}
