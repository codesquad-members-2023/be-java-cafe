package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Reply {

    private int id;
    private String contents;
    private LocalDateTime createDate;
    private int userId;
    private int articleId;


    public Reply() {
        this.createDate = LocalDateTime.now();
    }

    public Reply(String contents, int userId, int articleId) {
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}


