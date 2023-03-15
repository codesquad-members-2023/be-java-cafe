package kr.codesqaud.cafe.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private LocalDate createdDate;
    private static Long sequence = 0L;

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Article(String writer, String title, String contents) {
        this.id = generateId();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDate.now();
    }

    public boolean isIdEquals(Long articleId) {
        return this.id.equals(articleId);
    }

    private static Long generateId() {
        return ++ sequence;
    }
}
