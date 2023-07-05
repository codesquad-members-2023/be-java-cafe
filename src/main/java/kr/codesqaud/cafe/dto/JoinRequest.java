package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JoinRequest {
    @NotNull(message = "아이디를 입력해주세요. (2~80자)")
    @Size(min = 2, max = 80)
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요. (2~200자)")
    @Size(min = 2, max = 200)
    private String password;

    @NotNull(message = "닉네임을 입력해주세요. (2~50자)")
    @Size(min = 2, max = 50)
    private String nickname;

    @NotNull(message = "이메일을 입력해주세요. (2~300자)")
    @Email(message = "이메일 형식에 맞게 해주세요.")
    @Size(min = 2, max = 300)
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Member toEntity() {
        Member member = new Member();
        member.setUserId(userId);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setEmail(email);
        return member;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
