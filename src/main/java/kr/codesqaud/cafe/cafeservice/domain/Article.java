package kr.codesqaud.cafe.cafeservice.domain;


import jdk.jshell.Snippet;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private int replyCount;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.content = contents;
        this.createDate = LocalDateTime.now();
        this.replyCount = 0;
    }


    public Article() {
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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
                ", createDate=" + createDate +
                ", replyCount=" + replyCount +
                '}';
    }

}
