package com.smile.boot.data;

import lombok.Getter;
import org.slf4j.event.Level;

/**
 * 业务错误码分类
 *
 */
@Getter
public class StatusCode {

    public static final StatusCode SUCCESS = new StatusCode("0000", "请求成功", Level.INFO);

    public static final StatusCode UNKNOWN_EXCEPTION = new StatusCode("9999", "系统未知错误", Level.ERROR);

    public static final StatusCode REQUEST_PARAM_ILLEGAL = new StatusCode("0001", "请求参数非法", Level.WARN);

    public static final StatusCode REQUEST_SIGNED_INVALID = new StatusCode("0002", "请求验签异常",Level.WARN);

    public static final StatusCode REQUEST_DECRYPT_INVALID = new StatusCode("0003", "请求数据解密异常",Level.WARN);

    public static final StatusCode OBJECT_NOT_EXIST = new StatusCode("0004", "对象不存在",Level.WARN);

    public static final StatusCode SERVICE_NOT_AVAILABLE = new StatusCode("0005", "服务不可用", Level.ERROR);

    /**
     * 当前状态不可用
     *
     * @since 1.2.0
     */
    public static final StatusCode ILLEGAL_STATE = new StatusCode("0006", "当前状态不可用", Level.WARN);

    private final String code;
    private final String msg;
    private final Level level;

    public StatusCode(String code, String msg, Level level) {
        this.code = code;
        this.msg = msg;
        this.level = level;
    }

    public StatusCode code(String code) {
        return new StatusCode(code, msg, level);
    }

    public StatusCode message(String message) {
        return new StatusCode(code, message, level);
    }

    /**
     * 使用格式化消息构造 StatusCode
     *
     * @param format 格式化字符串
     * @param args 参数
     * @return StatusCode
     *
     * @see String#format(String, Object...)
     *
     * @since 1.5.1
     */
    public StatusCode message(String format, Object... args) {
        return new StatusCode(code, String.format(format, args), level);
    }

}
