package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @Builder
@NoArgsConstructor
public class UserListReponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private LocalDate createdDate;

    public UserListReponseDto(Long userId, String nickname, String email, LocalDate createdDate) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.createdDate = createdDate;
    }

    public static UserListReponseDto user(User user) {
        return UserListReponseDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .createdDate(user.getCreatedDate())
                .build();
    }
}
