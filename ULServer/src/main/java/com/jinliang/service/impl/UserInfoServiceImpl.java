package com.jinliang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinliang.common.entity.basic.Result;
import com.jinliang.common.exception.YjlException;
import com.jinliang.common.util.RedisUtils;
import com.jinliang.entity.dao.UserInfoDao;
import com.jinliang.entity.vo.UserInfoVO;
import com.jinliang.mapper.UserInfoMapper;
import com.jinliang.service.IUserInfoService;
import com.jinliang.ulenum.ResultEnum;
import com.jinliang.util.ConstantPropertiesUtil;
import com.jinliang.util.UserUtil;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDao> implements IUserInfoService {

    /**
     * 上传用户头像
     *
     * @param file
     * @return
     */
    @Override
    public Result uploadUserAvatar(MultipartFile file) throws IOException {
        TransferManager transferManager = ConstantPropertiesUtil.createTransferManager();
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
// 对象键(Key)是对象在存储桶中的唯一标识。
        String[] nameSplit = file.getOriginalFilename().split("\\.");
        String FileType = nameSplit[nameSplit.length - 1];
        String key = UserUtil.account + "." + FileType; // 当前登录用户
        InputStream inputStream = file.getInputStream() ;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        JSONObject json = new JSONObject();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);

        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            json = JSONObject.parseObject(JSONObject.toJSONString(uploadResult));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConstantPropertiesUtil.shutdownTransferManager(transferManager);
        }
        return Result.success(json);
    }

    /**
     * 登录方法
     *
     * @param userInfoVO
     * @return Result
     */
    @Override
    public Result login(UserInfoVO userInfoVO) {
        QueryWrapper<UserInfoDao> queryWrapper = new QueryWrapper<>();
        String account = "";
        String password = userInfoVO.getPassword();
        if (StringUtils.isNotEmpty(userInfoVO.getMail())) {
            account = userInfoVO.getMail();
        } else if(StringUtils.isNotEmpty(userInfoVO.getTelephone())) {
            account = userInfoVO.getTelephone();
        }else {
            throw new YjlException(400,"登录账号不能为空");
        }
        queryWrapper.eq("account",account).eq("password",password);
        Map<String, Object> map = getMap(queryWrapper);
        if (CollectionUtils.isEmpty(map)){
            return Result.fail(ResultEnum.LOGING_FAIL);
        } else {
            String token = getToken(account,password);
            if (account.equals("yejinliang")) {
                RedisUtils.putObjectForValue(account,token,30L, TimeUnit.SECONDS);
            } else {
                RedisUtils.putObjectForValue(account,token,30L, TimeUnit.MINUTES);
            }
            return Result.success(token,ResultEnum.LOGING_SUCCESS);
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
        userInfoDao.setTelephone(userInfoVO.getTelephone());
        userInfoDao.setMail(userInfoVO.getMail());
        userInfoDao.setUserName(userInfoVO.getUserName());
        userInfoDao.setPassword(userInfoVO.getPassword());
        if (StringUtils.isNotEmpty(userInfoVO.getMail())) {
            userInfoDao.setAccount(userInfoVO.getMail());
        }
        // 电话号码为账号的优先级高些
        if (StringUtils.isNotEmpty(userInfoVO.getTelephone())) {
            userInfoDao.setAccount(userInfoVO.getTelephone());
        }
        save(userInfoDao);
        return Result.success(userInfoDao);
    }

    private void checkUserVO(UserInfoVO userInfoVO){
        if (StringUtils.isEmpty(userInfoVO.getMail()) && StringUtils.isEmpty(userInfoVO.getTelephone())) {
            throw new YjlException(400,"登录账号不能为空");
        }
        if (StringUtils.isEmpty(userInfoVO.getUserName())) {
            throw new YjlException(400,"用户名称不能为空");
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
