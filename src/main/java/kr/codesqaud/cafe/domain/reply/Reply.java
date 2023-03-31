package kr.codesqaud.cafe.domain.reply;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reply {

	private String contents;
	private LocalDateTime writtenTime;
	private Long userSequence;
	private Long articleSequence;
	private String writer;


	public Reply() {

	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWrittenTime() {
		return writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public void setWrittenTime(LocalDateTime writtenTime) {
		this.writtenTime = writtenTime;
	}

	public Long getUserSequence() {
		return userSequence;
	}

	public void setUserSequence(Long userSequence) {
		this.userSequence = userSequence;
	}

	public Long getArticleSequence() {
		return articleSequence;
	}

	public void setArticleSequence(Long articleSequence) {
		this.articleSequence = articleSequence;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}
