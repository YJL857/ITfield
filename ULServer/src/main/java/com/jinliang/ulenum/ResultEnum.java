package com.jinliang.ulenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yejinliang
 * @create 2022-06-26 16:17
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200, "success","成功"),
    FAIL(1, "fail","失败");

    private final Integer code;
    private final String value;
    private final String cn;
    ResultEnum(int code, String value, String cn){
        this.code = code;
        this.value = value;
        this.cn = cn;
    }


}
