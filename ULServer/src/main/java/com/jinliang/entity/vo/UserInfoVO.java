package com.jinliang.entity.vo;

import lombok.Data;
import lombok.Getter;
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
    private String telephone;

    private String mail;

    private String userName;

    private String password;
}
