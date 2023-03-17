package kr.codesqaud.cafe.basic;


public class Article {
    private int articleId;
    private String writer;
    private String title;
    private String contents;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(Article article) {
        this.articleId = article.articleId;
        this.writer = article.writer;
        this.title = article.title;
        this.contents = article.contents;
    }

    public int getArticleId() { return articleId; }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
