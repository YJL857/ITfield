package com.jinliang.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinliang.common.util.RedisUtils;
import com.jinliang.entity.UserInfo;
import com.jinliang.entity.basic.Result;
import com.jinliang.mapper.UserInfoMapper;
import com.jinliang.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinliang.ulenum.ResultEnum;
import org.apache.catalina.session.StandardSession;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public Result login(String account, String password) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account).eq("password",password);
        Map<String, Object> map = getMap(queryWrapper);
        if (CollectionUtils.isEmpty(map)){
            return Result.fail(null, ResultEnum.LOGING_FAIL.getCode(),ResultEnum.LOGING_FAIL.getCn());
        } else {
            String token = getToken(account,password);
            if (account.equals("yejinliang")) {
                RedisUtils.putObjectForValue(account,token,30L, TimeUnit.SECONDS);
            } else {
                RedisUtils.putObjectForValue(account,token,30L, TimeUnit.MINUTES);
            }

            return Result.success(token,ResultEnum.LOGING_SUCCESS.getCode(),ResultEnum.LOGING_SUCCESS.getCn());
        }
    }

    /**
     * 生成token令牌
     *
     * @param account 用户名
     * @param password 密码
     * @return String token
     */
    private String getToken(String account, String password) {
        String secret = "abcdefg";
        return JWT.create().
                withClaim("account",account).
                withClaim("password",password).
                sign(Algorithm.HMAC256(secret));
    }



}
