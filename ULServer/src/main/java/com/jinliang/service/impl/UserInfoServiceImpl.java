package com.jinliang.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinliang.common.exception.YjlException;
import com.jinliang.common.util.RedisUtils;
import com.jinliang.entity.dao.UserInfoDao;
import com.jinliang.common.entity.basic.Result;
import com.jinliang.entity.vo.UserInfoVO;
import com.jinliang.mapper.UserInfoMapper;
import com.jinliang.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinliang.ulenum.ResultEnum;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.UUID;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDao> implements IUserInfoService {

    /**
     * 登录方法
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public Result login(String account, String password) {
        QueryWrapper<UserInfoDao> queryWrapper = new QueryWrapper<>();
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
     * 创建用户
     *
     * @param userInfoVO
     * @return
     */
    @Override
    public Result createUser(UserInfoVO userInfoVO) {
        checkUserVO(userInfoVO);
        UserInfoDao userInfoDao = new UserInfoDao();
        userInfoDao.setUserCode(userInfoVO.getUserCode());
        userInfoDao.setUserName(userInfoVO.getUserName());
        userInfoDao.setAccount(userInfoVO.getAccount());
        userInfoDao.setPassword(userInfoVO.getPassword());
        save(userInfoDao);
        return new Result(userInfoDao,200,"创建成功");
    }

    private void checkUserVO(UserInfoVO userInfoVO){
        if (StringUtils.isEmpty(userInfoVO.getUserCode())) {
            throw new YjlException(400,"用户编码不能为空");
        }
        if (StringUtils.isEmpty(userInfoVO.getUserName())) {
            throw new YjlException(400,"用户名称不能为空");
        }
        if (StringUtils.isEmpty(userInfoVO.getAccount())) {
            throw new YjlException(400,"账号不能为空");
        }
        if (StringUtils.isEmpty(userInfoVO.getPassword())) {
            throw new YjlException(400,"密码不能为空");
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
