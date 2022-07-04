package com.jinliang.entity.vo;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yejinliang
 * @create 2022-07-04 22:14
 */
@Getter
@Setter
@Data
public class UserInfoVO implements Serializable {

    private String userCode;

    private String userName;

    private String account;

    private String password;
}
