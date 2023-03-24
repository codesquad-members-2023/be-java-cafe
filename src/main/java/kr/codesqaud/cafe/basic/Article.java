package kr.codesqaud.cafe.basic;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Article {
    private int articleId;
    private String writer;
    private String title;
    private String contents;

    private Timestamp timeStamp;

    public Article() {
    }

    public Article(String writer, String title, String contents, Timestamp timeStamp) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.timeStamp = timeStamp;
    }

    public Article(int articleId, String writer, String title, String contents, Timestamp timeStamp) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.timeStamp = timeStamp;
    }

    public Article(Article article) {
        this.articleId = article.articleId;
        this.writer = article.writer;
        this.title = article.title;
        this.contents = article.contents;
        this.timeStamp = article.timeStamp;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getFormattedTime() {
        return timeStamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
