package com.smile.boot.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * 统一响应格式
 *
 */
@Data
@Builder
@ApiModel(value = "基础响应实体", description = "定义响应的基本字段")
public class BaseResponse<T> {

    @ApiModelProperty(value = "状态码", example = "0000")
    private String code;
    @ApiModelProperty(value = "状态码描述", example = "请求成功")
    private String msg;
    @ApiModelProperty("响应体")
    private T data;

    protected BaseResponse() {

    }

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return Objects.equals(StatusCode.SUCCESS.getCode(), code);
    }

    public static <U> BaseResponse<U> success() {
        return buildResponse(StatusCode.SUCCESS);
    }

    public static <U> BaseResponse<U> success(U data) {
        return buildResponse(StatusCode.SUCCESS, data);
    }

    /**
     * 构建成功响应
     *
     * @param data 数据实例
     * @param message 对应响应中的msg
     * @param <U> 实例类型
     * @return 响应实例
     * @since 1.2.0
     */
    public static <U> BaseResponse<U> success(U data, String message) {
        return buildResponse(StatusCode.SUCCESS.message(message), data);
    }

    public static <U> BaseResponse<U> error() {
        return buildResponse(StatusCode.UNKNOWN_EXCEPTION);
    }

    public static <U> BaseResponse<U> error(U data) {
        return buildResponse(StatusCode.UNKNOWN_EXCEPTION, data);
    }

    public static <U> BaseResponse<U> buildResponse(StatusCode statusCode) {
        return BaseResponse.<U>builder()
                .code(statusCode.getCode())
                .msg(statusCode.getMsg())
                .build();
    }

    public static <U> BaseResponse<U> buildResponse(StatusCode statusCode, U data) {
        return BaseResponse.<U>builder()
                .code(statusCode.getCode())
                .msg(statusCode.getMsg())
                .data(data).build();
    }

}
