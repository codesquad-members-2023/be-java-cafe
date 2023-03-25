package kr.codesqaud.cafe.domain;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Member {
    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

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

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
    public String getFormattedCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public String getFormattedUpdatedDate() {
        return updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
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

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate.toLocalDateTime();
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate.toLocalDateTime();
    }

    public Member() {}

    public Member(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Member(Long id, String userId, String nickname, String email, String password, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate.toLocalDateTime();
        this.updatedDate = updatedDate.toLocalDateTime();
    }

    public boolean equals(long userId) {
        return this.id == userId;
    }

    public boolean isNameEquals(String userName) {
        return this.nickname.equals(userName);
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
    public Member update(Member user) {
        this.nickname = user.nickname;
        this.email = user.email;
        this.password = user.password;
        return this;
    }


}
