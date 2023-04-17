package kr.codesqaud.cafe.domain.dto;

public class UserUpdateForm {

    private String oldPassword;
    private String newPassword;
    private String name;
    private String email;

    public UserUpdateForm(String oldPassword, String newPassword, String name, String email) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getName() {
        return name;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getEmail() {
        return email;
    }
}
