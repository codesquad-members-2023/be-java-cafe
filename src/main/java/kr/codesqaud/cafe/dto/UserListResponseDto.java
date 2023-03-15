package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.User;
import java.time.LocalDate;

public class UserListResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private LocalDate createdDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public UserListResponseDto(Long userId, String nickname, String email, LocalDate createdDate) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.createdDate = createdDate;
    }

    public UserListResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
    }
}
