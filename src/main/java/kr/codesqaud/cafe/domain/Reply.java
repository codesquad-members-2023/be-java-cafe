package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;

public class Reply {

    private int id;
    private int articleId;
    private String userId;
    private String contents;
    private Timestamp timestamp;

    public Reply() {}

    public Reply(int id, int articleId, String userId, String contents, Timestamp timestamp) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
