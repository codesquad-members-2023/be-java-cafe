package kr.codesqaud.cafe.cafeservice.dto;

import javax.validation.constraints.NotEmpty;

public class ArticleDTO {

    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String writer;

    @NotEmpty
    private String content;


    public ArticleDTO(Long id, String title, String writer, String content) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
