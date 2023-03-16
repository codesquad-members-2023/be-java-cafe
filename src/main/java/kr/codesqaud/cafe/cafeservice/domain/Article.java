package kr.codesqaud.cafe.cafeservice.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Article {
    private String writer;
    private String title;
    private String content;
    private Long id;
    private static AtomicLong sequence = new AtomicLong(0L);
    private Integer views;

    public Article(String writer, String title, String content, String views) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.id = idIncrease();
        this.views = 0;
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

    public Long getId() {
        return id;
    }

    public Integer getViews() {
        return views;
    }

    private  Long idIncrease() {
        return sequence.incrementAndGet();
    }
}
