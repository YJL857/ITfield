package com.jinliang.service;

import com.jinliang.entity.dao.UserInfoDao;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinliang.common.entity.basic.Result;
import com.jinliang.entity.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
public interface IUserInfoService extends IService<UserInfoDao> {

    Result login(UserInfoVO userInfoVO);

    Result createUser(UserInfoVO userInfoVO);

    Result uploadUserAvatar(MultipartFile file) throws IOException;

}
