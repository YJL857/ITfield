package com.jinliang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String rolecode;

    private String rolename;

    private String userid;

    private String creator;

    private LocalDateTime createtime;

    private String lastupdatot;

    private LocalDateTime updatetime;

    private String status;


}
