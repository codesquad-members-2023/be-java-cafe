package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;

public class Article {
    private int id;
    private String userId;
    private String title;
    private String contents;
    private Timestamp timestamp;

    public Article() {
    }

    public Article(String userId, String title, String contents, Timestamp timestamp) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
