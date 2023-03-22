package kr.codesqaud.cafe.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Article {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDate;


    public Article(Long id, String writer, String title, String contents, LocalDateTime writeDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeDate = writeDate;
    }

    public Long getId() {
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

    public String getWriteDate() {
        return writeDate.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));
    }

}

