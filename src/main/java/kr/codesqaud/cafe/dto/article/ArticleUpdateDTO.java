package kr.codesqaud.cafe.dto.article;

public class ArticleUpdateDTO {

    private String title;
    private String contents;

    public ArticleUpdateDTO() {}

    public ArticleUpdateDTO(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
