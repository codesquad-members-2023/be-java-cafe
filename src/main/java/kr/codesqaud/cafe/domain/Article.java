package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.util.List;

public class Article {
    private int id;
    private String userId;
    private String title;
    private String contents;
    private Timestamp timestamp;
    private List<Reply> replyList;

    public Article() {
    }

    public Article(int id, String userId, String title, String contents, Timestamp timestamp, List<Reply> replyList) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.timestamp = timestamp;
        this.replyList = replyList;
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

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
