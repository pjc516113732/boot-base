package com.smile.boot.data;


/**
 * 业务异常类
 *
 */
public class BusinessException extends RuntimeException {

    private transient StatusCode code = StatusCode.UNKNOWN_EXCEPTION;

    /** 是否打印异常栈，用于忽略那些可能不需要了解的异常 */
    private transient boolean logStacktrace = true;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.code = code.message(message);
    }

    public BusinessException(StatusCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public BusinessException(String message, StatusCode code) {
        super(message);
        this.code = code.message(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code.message(message);
    }

    public BusinessException(String message, Throwable cause, StatusCode code) {
        super(message, cause);
        this.code = code.message(message);
    }

    @Deprecated
    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Throwable cause, StatusCode code) {
        super(cause);
        this.code = code;
    }

    /**
     * 用于确定是否打印异常堆栈
     *
     * @param logStacktrace true 打印异常栈，false 不打印
     * @return 异常
     *
     * @since 1.1.3
     */
    public BusinessException withLogStackTrace(boolean logStacktrace) {
        this.logStacktrace = logStacktrace;
        return this;
    }

    public StatusCode getCode() {
        return code;
    }

    public boolean isLogStacktrace() {
        return logStacktrace;
    }
}
