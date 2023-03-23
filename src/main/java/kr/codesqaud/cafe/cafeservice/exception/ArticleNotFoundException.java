package kr.codesqaud.cafe.cafeservice.exception;

public class ArticleNotFoundException extends RuntimeException  {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
