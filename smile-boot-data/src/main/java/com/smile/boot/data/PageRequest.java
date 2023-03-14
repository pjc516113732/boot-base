package com.smile.boot.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 统一分页请求格式
 * 注意这个是基类可以被继承
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest implements Serializable {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final long serialVersionUID = -2371851765067886980L;
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = DEFAULT_PAGE_NUM;
    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize = DEFAULT_PAGE_SIZE;
}
