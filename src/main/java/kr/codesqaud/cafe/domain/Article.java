package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp timestamp;
    private int id;

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
}
