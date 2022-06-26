package com.jinliang.service;

import com.jinliang.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
public interface IUserInfoService extends IService<UserInfo> {

    boolean login(String account, String password);

}
