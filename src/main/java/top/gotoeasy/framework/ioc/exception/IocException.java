package top.gotoeasy.framework.ioc.exception;

/**
 * IOC模块异常
 * 
 * @since 2018/05
 * @author 青松
 */
public class IocException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     * 
     * @param message 消息
     */
    public IocException(String message) {
        super(message);
    }

    /**
     * 构造方法
     * 
     * @param cause 异常
     */
    public IocException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造方法
     * 
     * @param message 消息
     * @param cause 异常
     */
    public IocException(String message, Throwable cause) {
        super(message, cause);
    }

}
