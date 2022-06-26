package com.jinliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinliang.entity.UserInfo;
import com.jinliang.mapper.UserInfoMapper;
import com.jinliang.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public boolean login(String account, String password) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account).eq("password",password);
        Map<String, Object> map = getMap(queryWrapper);
        if (CollectionUtils.isEmpty(map)){
            return false;
        } else {
            return true;
        }
    }
}
