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
    private static Long sequence = 0L;

    public User(String email, String nickname, String password, LocalDate createdDate) {
        this.userId = idIncrease();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = createdDate;
    }

    public boolean isIdEquals(long userId) {
        return this.userId == userId;
    }

    public boolean isNameEquals(String userName) {
        return Objects.equals(this.nickname, userName);
    }

    private static Long idIncrease() {
        return LocalDate.now().hashCode() + ++sequence;
    }
}
