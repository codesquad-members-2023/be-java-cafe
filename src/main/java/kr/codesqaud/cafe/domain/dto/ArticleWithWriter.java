package kr.codesqaud.cafe.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ArticleWithWriter {

    private int id;
    private String title;
    private String contents;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    private int userId;
    private String writer;
    private int replyCount;

    public ArticleWithWriter() {
    }

    public ArticleWithWriter(int id, String title, String contents, LocalDateTime createDate, int userId, String writer, int replyCount) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.userId = userId;
        this.writer = writer;
        this.replyCount = replyCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
