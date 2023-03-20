package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private long index;
    private String writer;
    private String title;
    private String contents;

    private LocalDateTime createdTime;

    public Article(){
        this.createdTime = LocalDateTime.now();
        System.out.println(createdTime);
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public boolean isIndexEquals(long index) {
        return this.index == index;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
