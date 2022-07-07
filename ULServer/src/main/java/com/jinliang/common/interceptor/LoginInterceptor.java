package com.jinliang.common.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jinliang.common.exception.YjlException;
import com.jinliang.common.util.RedisUtils;
import com.jinliang.ulenum.ResultEnum;
import com.jinliang.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 * 1、配置好拦截器要拦截哪些请求
 * 2、把这些配置放在容器中
 * @author yejinliang
 * @create 2022-06-23 1:03
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 目标方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // 检查登录逻辑
        String authorization = request.getHeader("Authorization");
        DecodedJWT decode = JWT.decode(authorization);
        String account = decode.getClaim("account").asString();
        Object object = RedisUtils.getObjectForValue(account);
        String redisValue = "";
        if (object != null) {
            redisValue = object.toString();
        }
        if (StringUtils.isEmpty(redisValue)) {
            // 未登录
            throw new YjlException(ResultEnum.NO_LOGING.getCode(),ResultEnum.NO_LOGING.getCn());
        }
        if (redisValue.equals(authorization)) {
            UserUtil.account = account;
            return true;
        } else {
            // 鉴权失败
            throw new YjlException(ResultEnum.AUTHORIZATION_AUTHENTICATION_FAILED.getCode(),ResultEnum.AUTHORIZATION_AUTHENTICATION_FAILED.getCn());
        }
    }

    /**
     * 目标方法执行之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 前端渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
