package kr.codesqaud.cafe.domain;

import java.time.LocalDate;

public class Article {
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDate localDate;
    private int id;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDate = LocalDate.now();
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id + 1;
    }
}
