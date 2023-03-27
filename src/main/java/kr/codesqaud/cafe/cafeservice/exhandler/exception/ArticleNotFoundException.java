package kr.codesqaud.cafe.cafeservice.exhandler.exception;

public class ArticleNotFoundException extends RuntimeException  {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
