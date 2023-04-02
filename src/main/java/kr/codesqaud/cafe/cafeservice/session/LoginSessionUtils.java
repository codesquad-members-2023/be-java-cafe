package kr.codesqaud.cafe.cafeservice.session;

import kr.codesqaud.cafe.cafeservice.exhandler.exception.LoginNotFoundException;

public class LoginSessionUtils {
    private Long id;
    private String password;
    private String nickName;

    public LoginSessionUtils(Long id, String password, String nickName) {
        this.id = id;
        this.password = password;
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public static void sessionCheckId(Long id, LoginSessionUtils sessionUtils) {

        if (sessionUtils.getId() != id || sessionUtils == null) {
            throw new LoginNotFoundException("일치하는 아이디가 아니다");
        }
    }

    public boolean isValidPassword(String password) {
        if (this.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "LoginSessionUtils{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }
}
