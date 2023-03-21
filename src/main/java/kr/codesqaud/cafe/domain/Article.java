package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private Timestamp timestamp;

    public Article() {
    }

    public Article(String writer, String title, String contents, Timestamp timestamp) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
