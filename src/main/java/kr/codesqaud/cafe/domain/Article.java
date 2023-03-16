package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private int id;
    private final String writer;
    private final String title;
    private final String contents;

    private final LocalDateTime createDate;

    private int replyCount;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
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

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public int getReplyCount() {
        return replyCount;
    }
}
