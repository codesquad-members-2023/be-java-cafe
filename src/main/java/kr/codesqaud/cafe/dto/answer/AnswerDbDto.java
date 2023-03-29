package kr.codesqaud.cafe.dto.answer;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerDbDto {
    private long id;
    private String contents;
    private long articleId;
    private long writerIndex;
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime memberCreatedAt;
    private LocalDateTime memberUpdatedAt;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

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

    public long getWriterIndex() {
        return writerIndex;
    }

    public void setWriterIndex(long writerIndex) {
        this.writerIndex = writerIndex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getMemberCreatedAt() {
        return memberCreatedAt;
    }

    public void setMemberCreatedAt(LocalDateTime memberCreatedAt) {
        this.memberCreatedAt = memberCreatedAt;
    }

    public LocalDateTime getMemberUpdatedAt() {
        return memberUpdatedAt;
    }

    public void setMemberUpdatedAt(LocalDateTime memberUpdatedAt) {
        this.memberUpdatedAt = memberUpdatedAt;
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
        answer.setWriter(new Member(this.writerIndex, this.userId, this.nickname, this.email, this.password, this.createdDate, this.updatedDate));
        answer.setArticleId(this.articleId);
        answer.setCreatedDate(this.createdDate);
        answer.setUpdatedDate(this.updatedDate);
        return answer;
    }
}
