package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReplyDto {

	private String writer;
	private Long userSequence;
	private LocalDateTime writtenTime;
	private String contents;
	private Long articleSequence;

	public ReplyDto() {

	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWrittenTime() {
		return writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public void setWrittenTime(LocalDateTime writtenTime) {
		this.writtenTime = writtenTime;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getArticleSequence() {
		return articleSequence;
	}

	public void setArticleSequence(Long articleSequence) {
		this.articleSequence = articleSequence;
	}
}
