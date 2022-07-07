package com.jinliang.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jinliang.common.entity.basic.BasicInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
@Getter
@Setter
@TableName("user_info")
public class UserInfoDao extends BasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String telephone;

    private String mail;

    private String userCode;

    private String userName;

    private String account;

    private String password;
}
