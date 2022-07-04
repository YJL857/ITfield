package com.jinliang.common.exception;

import com.jinliang.common.entity.basic.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常
 *
 * @author yejinliang
 * @create 2022-07-01 0:24
 */
@ControllerAdvice // AOP
@ResponseBody
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        StringBuffer message = new StringBuffer();
        message.append(e.getMessage());
        message.append("------");
        message.append(e.getStackTrace()[0].toString());
        return Result.fail(null, 500, message.toString());
    }

    // 自定义异常处理
    @ExceptionHandler(YjlException.class)
    public Result error(YjlException e) {
        return Result.fail(null, e.getCode(), e.getMsg());
    }

}
