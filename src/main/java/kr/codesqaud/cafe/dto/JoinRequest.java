package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JoinRequest {
    @NotNull(message = "아이디를 입력해주세요.")
    @Size(min = 8, max = 80, message = "회원 아이디는 8~80자까지 가능합니다.")
    private String userId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 200, message = "비밀번호는 8~200자까지 가능합니다.")
    private String password;

    @NotNull(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 50, message = "닉네임은 2~50자까지 가능합니다.")
    private String nickname;

    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞게 해주세요.")
    @Size(min = 5, max = 300, message = "이메일은 5~300자까지 가능합니다.")
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
