package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reply {
    private long id;
    private String writer;
    private String contents;
    private LocalDateTime time;
    private long articleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time.toLocalDateTime();
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }
}
