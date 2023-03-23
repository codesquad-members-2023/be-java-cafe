package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private int id;

    private String writer;

    private String title;
    private String contents;

    private LocalDateTime createDate;
    private int userId;

    private int replyCount;

    public Article() {
        this.createDate = LocalDateTime.now();
        this.replyCount = 0;
    }

    public Article(int userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.replyCount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReplyCount() {
        return replyCount;
    }

}
