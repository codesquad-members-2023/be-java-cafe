package kr.codesqaud.cafe.domain.article;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Article {

	private Long articleSequence;
	private String writer;
	private String title;
	private String contents;
	private LocalDateTime writtenTime;

	public Article() {
	}

	public Article(String writer, String title, String contents, LocalDateTime writtenTime) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.writtenTime = writtenTime;
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

	public void setWrittenTime(LocalDateTime writtenTime) {
		this.writtenTime = writtenTime;
	}

	public String getWrittenTime() {
		String date = writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return date;
	}
}
