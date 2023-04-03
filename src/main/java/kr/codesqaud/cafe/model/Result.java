package kr.codesqaud.cafe.model;

public class Result {

    private String errorMessage;

    public Result() {
    }

    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
