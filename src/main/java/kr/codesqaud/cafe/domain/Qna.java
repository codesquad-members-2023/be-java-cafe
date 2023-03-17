package kr.codesqaud.cafe.domain;

public class Qna {

    private int qnaIndex;
    private String writer;
    private String title;
    private String contents;

    public Qna(int qnaIndex, String writer, String title, String contents) {
        this.qnaIndex = qnaIndex;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public int getQnaIndex() {
        return qnaIndex;
    }

    public void setQnaIndex(int qnaIndex) {
        this.qnaIndex = qnaIndex;
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
}
