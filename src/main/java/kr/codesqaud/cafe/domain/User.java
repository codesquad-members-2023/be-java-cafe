package kr.codesqaud.cafe.domain;


import java.time.LocalDate;
import java.util.Objects;

public class User {
    private long userId;
    private String email;
    private String nickname;
    private String password;
    private LocalDate createdDate;
    private static Long sequence = 0L;

    public long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public static Long getSequence() {
        return sequence;
    }

    public User(String email, String nickname, String password, LocalDate createdDate) {
        this.userId = generateUserId();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = createdDate;
    }

    public User(long userId, String email, String nickname, String password, LocalDate createdDate) {
        this.userId = userId;
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

    private static Long generateUserId() {
        return LocalDate.now().hashCode() + ++sequence;
    }
}
