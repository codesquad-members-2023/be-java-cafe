package kr.codesqaud.cafe.domain.article;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Article {

	private Long articleSequence;
	private String writer;
	private String title;
	private String contents;
	private String writtenTime;

	public Article(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		writtenTime = formattingDate(timestamp);
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

	public String getWrittenTime() {
		return writtenTime;
	}

	private String formattingDate(Timestamp timestamp) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formattedDate = simpleDateFormat.format(timestamp).toString();
		return formattedDate;
	}
}
