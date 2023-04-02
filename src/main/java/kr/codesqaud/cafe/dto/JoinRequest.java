package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JoinRequest {
    @NotNull
    @Size(min = 8, max = 100)
    private String userId;

    @NotNull
    @Size(min = 8, max = 200)
    private String password;

    @NotNull
    @Size(min = 2, max = 50)
    private String nickname;

    @NotNull
    @Email
    @Size(min = 5, max = 300)
    private String email;

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
