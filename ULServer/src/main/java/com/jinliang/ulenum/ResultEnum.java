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
    FAIL(401, "fail","失败"),

    LOGING_SUCCESS(200, "loging success","登录成功"),
    LOGING_FAIL(401,"loging fail","登录失败"),
    NO_LOGING(401,"no loging","当前用户未登录，请先登录"),
    AUTHORIZATION_AUTHENTICATION_FAILED(403,"Authorization authentication failed","权限认证失败");


    private final Integer code;
    private final String value;
    private final String cn;
    ResultEnum(int code, String value, String cn){
        this.code = code;
        this.value = value;
        this.cn = cn;
    }


}
