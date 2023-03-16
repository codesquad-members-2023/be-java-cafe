package kr.codesqaud.cafe.domain;

import java.time.LocalDate;

public class Article {
    private final Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private LocalDate createdDate;

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Article(String writer, String title, String contents, long size) {
        this.id = size +1;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDate.now();
    }

    public boolean isIdEquals(Long articleId) {
        return this.id.equals(articleId);
    }
}
