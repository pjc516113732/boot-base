package com.smile.boot.autoconfigure.advice;


import com.smile.boot.data.BaseResponse;
import com.smile.boot.data.BusinessException;
import com.smile.boot.data.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 *
 */
@Slf4j
@Order
@ConditionalOnClass({BaseResponse.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
@ConditionalOnProperty(value = "insnail.boot.advice.enabled", matchIfMissing = true)
@Configuration
@RestControllerAdvice
public class ExceptionalHandler {

    /**
     * 处理参数校验不通过的异常
     *
     * @param e 框架层面抛出的异常
     * @return 统一的响应报文
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = buildBindErrorMsg(bindingResult);
        String msg = String.format("请求参数校验失败: [%s]", errorMsg);
        String methodName = String.format("%s.%s", e.getParameter().getDeclaringClass().getName(), e.getParameter().getExecutable().getName());
        log.warn(String.format("%s: %s", methodName, msg), e);
        return BaseResponse.buildResponse(StatusCode.REQUEST_PARAM_ILLEGAL.message(msg));
    }

    private String buildBindErrorMsg(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(objectError -> {
                    if (objectError instanceof FieldError) {
                        FieldError fieldError = (FieldError) objectError;
                        return String.format("{%s: %s}", fieldError.getField(), fieldError.getDefaultMessage());
                    } else {
                        return String.format("{%s: %s}", objectError.getObjectName(), objectError.getDefaultMessage());
                    }
                })
                .collect(Collectors.joining(", "));
    }

    @ExceptionHandler({ConstraintViolationException.class, MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    public BaseResponse<Object> handleIllegalException(Exception e) {
        String msg = String.format("请求参数校验失败: [%s]", e.getMessage());
        log.warn(msg, e);
        return BaseResponse.buildResponse(StatusCode.REQUEST_PARAM_ILLEGAL.message(msg));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BaseResponse<Object> handleHttpRequestMethodNotSupportedException(Exception e) {
        String msg = String.format("请求方法不对: [%s]", e.getMessage());
        log.warn(msg, e);
        return BaseResponse.buildResponse(StatusCode.REQUEST_PARAM_ILLEGAL.message(msg));
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Object> handle(BusinessException e) {
        StatusCode code = e.getCode();
        if (Level.ERROR.equals(code.getLevel())) {
            if (e.isLogStacktrace())
                log.error(code.getMsg(), e);
            else
                log.error(code.getMsg());
        } else {
            if (e.isLogStacktrace())
                log.warn(code.getMsg(), e);
            else
                log.warn(code.getMsg());
        }
        return BaseResponse.buildResponse(code);
    }

    /**
     * 处理请求参数序列化异常问题
     *
     * @param e HttpMessageNotReadableException
     * @return 错误提示信息
     * @since 1.4.2
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<Object> handle(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.buildResponse(StatusCode.REQUEST_PARAM_ILLEGAL.message("请求参数不正确！请确认日期、数字及枚举等类型填写格式正确"));
    }

    /**
     * 处理 IllegalArgumentException 通用异常
     *
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<Object> handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return BaseResponse.buildResponse(StatusCode.REQUEST_PARAM_ILLEGAL.message(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> handle(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResponse.buildResponse(StatusCode.UNKNOWN_EXCEPTION);
    }

}
