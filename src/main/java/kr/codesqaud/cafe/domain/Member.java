package kr.codesqaud.cafe.domain;


import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Member {
    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private Timestamp createdDate;
    private Timestamp updatedDate;

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

    public String getCreatedDate() {
        return createdDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public String getUpdatedDate() {
        return updatedDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
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
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
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
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public boolean equals(long userId) {
        return this.id == userId;
    }

    public boolean isNameEquals(String userName) {
        return Objects.equals(this.nickname, userName);
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
