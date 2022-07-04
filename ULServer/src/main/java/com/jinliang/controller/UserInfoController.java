package com.jinliang.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinliang.common.annotation.ResponseResult;
import com.jinliang.common.exception.YjlException;
import com.jinliang.entity.dao.UserInfoDao;
import com.jinliang.common.entity.basic.Result;
import com.jinliang.entity.vo.UserInfoVO;
import com.jinliang.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
@Api(value = "用户管理",tags = "用户管理")
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
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @PostMapping("/createUser")
    public Result createUser(@RequestBody UserInfoVO userVO) {
        return iUserInfoService.createUser(userVO);
    }

    @ApiOperation(value = "登录", notes = "登录")
    @GetMapping("/login")
    @ResponseResult
    public Result login(@RequestParam("account") String account, @RequestParam("password") String password) {
        return iUserInfoService.login(account, password);
    }

    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    @GetMapping("/findUserPage")
    public Result findUserPage(@RequestParam("curPage") int curPage, @RequestParam("pageSize") int pageSize) {
        Page<UserInfoDao> userInfoPage = new Page(curPage, pageSize); // 前端的page
        Page<UserInfoDao> page = new LambdaQueryChainWrapper<>(iUserInfoService.getBaseMapper()).page(userInfoPage);
        List<UserInfoDao> userInfoDaoList = page.getRecords();
        return Result.success(page);
    }

    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @GetMapping("/findUserById")
    public UserInfoDao findUserById(@RequestParam("id") Long id) {
        return iUserInfoService.getById(id);
    }

    @ApiOperation(value = "根据id更新用户", notes = "根据id更新用户")
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody UserInfoDao userVO) {
        return iUserInfoService.updateById(userVO);
    }

    @ApiOperation(value = "根据id删除用户", notes = "根据id删除用户")
    @GetMapping("/deleteUser")
    public boolean deleteUser(@RequestParam("id") Long id) {
        return iUserInfoService.removeById(id);
    }

    @ApiOperation(value = "模拟异常处理（自定义异常）", notes = "模拟异常处理（自定义异常）")
    @GetMapping("/exception_my")
    public boolean exception_my(@RequestParam("id") Long id) {
        try {
            int i = 10/0; // 模拟异常
        } catch (Exception e) {
            throw new YjlException(400,"自定义异常处理，程序错了");
        }
        return true;
    }

    @ApiOperation(value = "模拟异常处理（全局异常）", notes = "模拟异常处理（全局异常）")
    @GetMapping("/exception_all")
    public boolean exception_all(@RequestParam("id") Long id) {
        int i = 10/0; // 模拟异常
        return true;
    }

}
