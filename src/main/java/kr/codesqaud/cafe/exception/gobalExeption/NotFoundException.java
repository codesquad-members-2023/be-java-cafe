package kr.codesqaud.cafe.exception.gobalExeption;

public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException() {};

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {return message;}
}
