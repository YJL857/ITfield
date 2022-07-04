package com.jinliang.service;

import com.jinliang.entity.dao.UserInfoDao;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinliang.common.entity.basic.Result;
import com.jinliang.entity.vo.UserInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
public interface IUserInfoService extends IService<UserInfoDao> {

    Result login(String account, String password);

    Result createUser(UserInfoVO userInfoVO);

}
