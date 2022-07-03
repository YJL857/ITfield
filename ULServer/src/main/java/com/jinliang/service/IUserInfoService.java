package com.jinliang.service;

import com.jinliang.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinliang.entity.basic.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
public interface IUserInfoService extends IService<UserInfo> {

    Result login(String account, String password);

}
