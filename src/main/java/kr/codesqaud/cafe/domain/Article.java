package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private final String writer;
    private final String title;
    private final String content;
    private Long id;
    private LocalDateTime creationTime;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Article(String writer, String title, String content, Long id, LocalDateTime creationTime) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.id = id;
        this.creationTime = creationTime;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getIndex() {
        return id;
    }

    public Long getId() {
        return id;
    }

    public String getCreationTime() {
        return creationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setId(long id) {
        this.id = id;
    }
}
