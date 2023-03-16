package kr.codesqaud.cafe.domain;


import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private long userId;
    private String email;
    private String nickname;
    private String password;
    private LocalDate createdDate;
    private static AtomicLong sequence = new AtomicLong(0L);

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

    public User(String email, String nickname, String password) {
        this.userId = generateId();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = LocalDate.now();
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

    public long generateId() {
        return sequence.incrementAndGet();
    }
}
