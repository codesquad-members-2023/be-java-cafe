package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;

import java.time.LocalDate;

public class UserJoinRequestDto {
    private String email;
    private String nickname;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserJoinRequestDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public User toEntity() {
        return new User(email, nickname, password, LocalDate.now());
    }
}
