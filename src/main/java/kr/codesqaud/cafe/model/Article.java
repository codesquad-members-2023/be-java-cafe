package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.cglib.core.Local;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime creationTime;

    public Article(long id, String writer, String title, String contents, LocalDateTime creationTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.creationTime = creationTime;
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        creationTime = LocalDateTime.now();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

}
