package kr.codesqaud.cafe.domain;

import java.util.Date;

public class Article {

    private long id;
    private String writer;
    private String title;
    private String contents;

    public Article(long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
