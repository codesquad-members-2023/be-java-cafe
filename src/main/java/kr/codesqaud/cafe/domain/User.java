package kr.codesqaud.cafe.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter @Builder
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate localDate;

    public User(Long userId, String username, String password, String email, String phone, LocalDate localDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.localDate = localDate;
    }
}
