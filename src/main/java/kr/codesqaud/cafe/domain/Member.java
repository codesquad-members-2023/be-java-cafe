package kr.codesqaud.cafe.domain;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Member {
    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private static final AtomicLong sequence = new AtomicLong(0L);

    public Long getId() {
        return id;
    }

    public String getUserId() {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Member() { // JDBC 템플릿 쓸 때 기본 생성자를 열어야함
    }

    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Member(String userId, String email, String nickname, String password) {
        this.id = generateId();
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdDate = LocalDateTime.now();
    }

    public Member(Long id, String userId, String nickname, String email, String password, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
    }

    public boolean equals(long userId) {
        return this.id == userId;
    }

    public boolean isNameEquals(String userName) {
        return Objects.equals(this.nickname, userName);
    }

    public long generateId() {
        return sequence.incrementAndGet();
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
    public void update(Member user) {
        this.nickname = user.nickname;
        this.email = user.email;
        this.password = user.password;
    }
}
