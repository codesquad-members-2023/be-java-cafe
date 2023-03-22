package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String contents;

    private LocalDateTime time;

    public Timestamp getTime() {
        return Timestamp.valueOf(time);
    }

    public void setTime(Timestamp time) {
        this.time = time.toLocalDateTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIndexEquals(long id) {
        return this.id == id;
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
}
