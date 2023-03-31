package kr.codesqaud.cafe.model;

public class Result {

    private boolean isOk;
    private String errorMessage;

    public Result() {
    }

    public Result(boolean isOk) {
        this.isOk = isOk;
    }

    public Result(boolean isOk, String errorMessage) {
        this.isOk = isOk;
        this.errorMessage = errorMessage;
    }

    public Result ok() {
        return new Result(true);
    }

    public Result fail() {
        return new Result(false, "삭제에 실패했습니다.");
    }

    public boolean isOk() {
        return isOk;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
