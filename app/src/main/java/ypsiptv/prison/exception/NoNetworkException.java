package ypsiptv.prison.exception;


public class NoNetworkException extends BaseException {
    public NoNetworkException() {
        super(ErrorCode.NO_NETWORK_CODE, ErrorCode.NO_NETWORK_MSG);
    }
    public NoNetworkException(int resultCode, String message) {
        super(resultCode, message);
    }
}
