package com.jinliang.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.jinliang.common.entity.basic.BasicInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author yejinliang
 * @since 2022-07-03
 */
@Getter
@Setter
@TableName("role_info")
public class RoleInfoDao extends BasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleCode;

    private String roleName;
}
