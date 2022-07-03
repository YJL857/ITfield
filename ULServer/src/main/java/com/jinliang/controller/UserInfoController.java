package com.jinliang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinliang.annotation.ResponseResult;
import com.jinliang.common.exception.YjlException;
import com.jinliang.entity.UserInfo;
import com.jinliang.entity.basic.Result;
import com.jinliang.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @ApiOperation(value = "注册用户", notes = "注册用户")
    @PostMapping("/createUser")
    public boolean createUser(@RequestBody UserInfo userVO) {
        return iUserInfoService.save(userVO);
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
        Page<UserInfo> userInfoPage = new Page(curPage, pageSize); // 前端的page
        Page<UserInfo> page = new LambdaQueryChainWrapper<>(iUserInfoService.getBaseMapper()).page(userInfoPage);
        List<UserInfo> userInfoList = page.getRecords();
        return Result.success(page);
    }

    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    @GetMapping("/findUserById")
    public UserInfo findUserById(@RequestParam("id") Long id) {
        return iUserInfoService.getById(id);
    }

    @ApiOperation(value = "根据id更新用户", notes = "根据id更新用户")
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody UserInfo userVO) {
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
