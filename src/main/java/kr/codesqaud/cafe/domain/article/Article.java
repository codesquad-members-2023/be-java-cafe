package kr.codesqaud.cafe.domain.article;

import kr.codesqaud.cafe.domain.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Article {
    private Long id;
    private Writer writer;
    private String title;
    private String contents;
    private List<Answer> answerList;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public String getWriterNickName() {
        return writer.getNickname();
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getFormattedCreatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getFormattedUpdatedDate() {
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Article() {
    }

    public Article(Member writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public boolean equals(Long articleId) {
        return this.id.equals(articleId);
    }

}
