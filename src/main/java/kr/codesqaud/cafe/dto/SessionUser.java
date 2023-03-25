package kr.codesqaud.cafe.dto;

public class SessionUser {
    public static final String SESSION_USER = "SessionedUser";
    private long id;
    private String nickName;

    public long getId() {
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

    public boolean equals(long id) {
        return this.id == id;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
