package kr.codesqaud.cafe.cafeservice.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdDate;
    private int replyCount;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.content = contents;
        this.replyCount = 0;
    }

    public Article() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createdDate +
                ", replyCount=" + replyCount +
                '}';
    }

}
