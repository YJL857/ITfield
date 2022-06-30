package com.jinliang.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 *
 * @author yejinliang
 * @create 2022-07-01 0:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YjlException extends RuntimeException {

    private Integer code;

    private String msg;
}
