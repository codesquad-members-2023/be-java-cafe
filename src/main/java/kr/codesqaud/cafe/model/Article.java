package kr.codesqaud.cafe.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Article {
    private int id;
    private final String writer;
    private String title;
    private String contents;
    private String time;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = formattingTime();
    }

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String formattingTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public void giveId(int listSize) {
        id = listSize;
    }
}
