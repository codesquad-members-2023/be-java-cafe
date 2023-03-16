package kr.codesqaud.cafe.article;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
    private final String writer;
    private final String title;
    private final String contents;
    private LocalDate localDateTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDateTime = LocalDate.now();
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

    public LocalDate getLocalDateTime() {
        return localDateTime;
    }
}
