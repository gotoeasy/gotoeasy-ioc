package top.gotoeasy.framework.ioc.exception;

public class IocException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IocException(String message) {
        super(message);
    }

    public IocException(Throwable cause) {
        super(cause);
    }

    public IocException(String message, Throwable cause) {
        super(message, cause);
    }

}
