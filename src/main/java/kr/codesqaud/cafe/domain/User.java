package kr.codesqaud.cafe.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter @Builder
public class User {
    private long userId;
    private String email;
    private String nickname;
    private String password;
    private LocalDate createdDate;

    public User(long userId, String email, String nickname, String password, LocalDate localDate) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = localDate;
    }

    public boolean isIdEquals(long userId) {
        return this.userId == userId;
    }

    public boolean isNameEquals(String userName) {
        return Objects.equals(this.nickname, userName);
    }
}
