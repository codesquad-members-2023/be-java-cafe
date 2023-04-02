package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.article.Reply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyResponse {
    private long answerIndex;
    private long writerIndex;
    private String nickname;
    private String contents;
    private LocalDateTime createdDate;

    public long getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(long answerIndex) {
        this.answerIndex = answerIndex;
    }

    public long getWriterIndex() {
        return writerIndex;
    }

    public void setWriterIndex(long writerIndex) {
        this.writerIndex = writerIndex;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getFormattedCreatedDate() {
        return this.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public static ReplyResponse toDto(Reply answer) {
        ReplyResponse answerDBDto = new ReplyResponse();
        answerDBDto.setAnswerIndex(answer.getId());
        answerDBDto.setContents(answer.getContents());
        answerDBDto.setWriterIndex(answer.getWriterId());
        answerDBDto.setNickname(answer.getWriterNickname());
        answerDBDto.setCreatedDate(answer.getCreatedDate());
        return answerDBDto;
    }

    public long getWriterId() {
        return writerIndex;
    }
}
