package kr.codesqaud.cafe.cafeservice.dto;

import javax.validation.constraints.NotEmpty;

public class ArticleDTO {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
