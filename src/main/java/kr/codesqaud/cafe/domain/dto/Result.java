package kr.codesqaud.cafe.domain.dto;

public class Result {

    private String message;

    public Result(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
