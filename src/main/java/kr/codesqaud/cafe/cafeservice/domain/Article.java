package kr.codesqaud.cafe.cafeservice.domain;

public class Article {
    private String writer;
    private String title;
    private String content;
    private Long id;
    private static long sequence = 0L; //static 사용
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

    public static long getSequence() {
        return sequence;
    }

    public Integer getViews() {
        return views;
    }

    private Long idIncrease() {
        return ++sequence;
    }
}
