package kr.codesqaud.cafe.dto;

public class UserForm {
    private String email;
    private String nickName;
    private String password;

    public UserForm(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }
}
