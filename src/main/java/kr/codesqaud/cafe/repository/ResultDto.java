package kr.codesqaud.cafe.repository;

public class ResultDto {
    private String message;
    private boolean valid;

    public ResultDto() {
    }

    public ResultDto(String message, boolean valid) {
        this.message = message;
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "message='" + message + '\'' +
                ", valid=" + valid +
                '}';
    }
}
