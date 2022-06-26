package com.jinliang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinliang.annotation.ResponseResult;
import com.jinliang.entity.UserInfo;
import com.jinliang.entity.basic.Result;
import com.jinliang.service.IUserInfoService;
import com.jinliang.ulenum.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private IUserInfoService iUserInfoService;

    /**
     * 注册用户
     *
     * @param userVO
     * @return
     */
    @PostMapping("/createUser")
    public boolean createUser(@RequestBody UserInfo userVO) {
        return iUserInfoService.save(userVO);
    }

    @GetMapping("/login")
    @ResponseResult
    public boolean login(@RequestParam("account") String account, @RequestParam("password") String password) {
        return iUserInfoService.login(account, password);
    }

    @GetMapping("/findUserPage")
    @ResponseResult
    public Result findUserPage(@RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize) {
        Page<UserInfo> userInfoPage = new Page(curPage, pageSize); // 前端的page
        Page<UserInfo> page = new LambdaQueryChainWrapper<>(iUserInfoService.getBaseMapper()).page(userInfoPage);
        List<UserInfo> userInfoList = page.getRecords();
        return Result.success(page);
    }

    @GetMapping("/findUserById")
    public UserInfo findUserById(@RequestParam("id") Long id) {
        return iUserInfoService.getById(id);
    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody UserInfo userVO) {
        return iUserInfoService.updateById(userVO);
    }

    @GetMapping("/deleteUser")
    public boolean deleteUser(@RequestParam("id") Long id) {
        return iUserInfoService.removeById(id);
    }

}
