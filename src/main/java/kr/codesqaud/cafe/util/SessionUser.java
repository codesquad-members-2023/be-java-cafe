package kr.codesqaud.cafe.util;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class SessionUser {
    public static final String SESSION_USER = "sessionedUser";
    private Long id;
    private String nickName;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public SessionUser(long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public static SessionUser getSessionUser(HttpSession session) {
        try {
            return (SessionUser) session.getAttribute(SESSION_USER);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean equals(Long id) {
        return Objects.equals(this.id, id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
