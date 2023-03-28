package kr.codesqaud.cafe.domain.dto;

import java.time.LocalDateTime;

public class ReplyWithUser {

    private int id;
    private int userId;
    private String userName;
    private String contents;
    private LocalDateTime createDate;

    public ReplyWithUser() {
    }

    public ReplyWithUser(int id, int userId, String userName, String contents, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.contents = contents;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
