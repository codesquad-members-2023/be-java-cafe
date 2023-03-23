package kr.codesqaud.cafe.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ArticleForm {
    private String writer;
    private String title;
    private String content;
    private Timestamp creationTime;

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
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
}
