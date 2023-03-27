package kr.codesqaud.cafe.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import kr.codesqaud.cafe.service.ArticleService;

public class Article {
    private int articleId;
    private final String userId;
    private String title;
    private String contents;
    private String time;
    private boolean deleted;

    public Article(String userId, String title, String contents) {
        this.articleId = ArticleService.getArticleSize();
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.time = formattingTime();
        this.deleted = false;
    }

    public int getId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }

    public int getArticleId() {
        return articleId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    public String formattingTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(Calendar.getInstance().getTime());
    }
}
