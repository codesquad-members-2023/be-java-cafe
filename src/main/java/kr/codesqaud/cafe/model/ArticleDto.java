package kr.codesqaud.cafe.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDto {

    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime creationTime;

    public ArticleDto(long id, String writer, String title, String contents, LocalDateTime creationTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.creationTime = creationTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getFormattedCreationTime() {
        return creationTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
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
