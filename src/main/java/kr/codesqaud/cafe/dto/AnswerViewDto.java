package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerViewDto {
    private long id;
    private String contents;
    private long articleId;
    private long writerId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String writerNickname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getWriterId() {
        return writerId;
    }

    public void setWriterId(long writerId) {
        this.writerId = writerId;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public void setWriterNickname(String writerNickname) {
        this.writerNickname = writerNickname;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFomattedCreatedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public String getFomattedUpdatedDate() {
        return this.updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public Answer toDomain() {
        Answer answer = new Answer();
        answer.setId(this.id);
        answer.setContents(this.contents);
        answer.setWriter(new Member(this.writerId, this.writerNickname));
        answer.setArticleId(this.articleId);
        answer.setCreatedDate(this.createdDate);
        answer.setUpdatedDate(this.updatedDate);
        return answer;
    }

}
