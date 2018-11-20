package ypsiptv.prison.exception;


public class BaseException extends RuntimeException {

    private int resultCode;
    private String message;

    public BaseException(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

    public int getResultCode() {
        return resultCode;
    }
}
