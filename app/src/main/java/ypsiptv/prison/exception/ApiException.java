package ypsiptv.prison.exception;


public class ApiException extends BaseException {
    public ApiException(int resultCode, String message) {
        super(resultCode, message);
    }
}
