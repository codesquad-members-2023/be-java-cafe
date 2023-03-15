package kr.codesqaud.cafe.dto;

public class UserForm {
    private String email;
    private String userId;
    private String password;

    public UserForm(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
