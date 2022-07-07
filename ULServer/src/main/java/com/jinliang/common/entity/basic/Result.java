package com.jinliang.common.entity.basic;

import com.jinliang.ulenum.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yejinliang
 * @create 2022-06-26 17:04
 */
@Data
@AllArgsConstructor // 有参构造
public class Result {
    private Object data;

    private Integer code;

    private String message;

    public Result(ResultEnum resultEnum) {
        super();
    }

    public static Result success(Object data) {
        return new Result(data, ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getValue());
    }

    public static Result success(Object data, ResultEnum resultEnum) {
        return new Result(data,resultEnum.getCode(),resultEnum.getCn());
    }

    public static Result fail(Object data, Integer code, String message) {
        return new Result(data, code, message);
    }

    public static Result fail(ResultEnum resultEnum) {
        return new Result(null, resultEnum.getCode(),resultEnum.getCn());
    }
}
