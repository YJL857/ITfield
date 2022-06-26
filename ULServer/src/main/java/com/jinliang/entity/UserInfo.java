package com.jinliang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.jinliang.entity.basic.BasicInfo;
import lombok.Getter;
import lombok.Setter;

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
public class UserInfo extends BasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String usercode;

    private String username;

    private String account;

    private String password;

    private String roleid;
}
