package ypsiptv.prison.exception;


public class InvalidTokenException extends BaseException {
    public InvalidTokenException(int resultCode, String message) {
        super(resultCode, message);
    }
}
