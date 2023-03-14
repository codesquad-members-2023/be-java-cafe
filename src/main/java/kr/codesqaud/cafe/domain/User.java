package kr.codesqaud.cafe.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter @Builder
public class User {
    private Long userId;
    private String email;
    private String nickname;
    private String password;
    private LocalDate createdDate;

    public User(Long userId, String email, String nickname, String password, LocalDate localDate) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = localDate;
    }
}
