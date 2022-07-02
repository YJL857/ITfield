package com.jinliang.common.config;

import com.jinliang.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 *
 * @author yejinliang
 * @create 2022-06-23 1:01
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/user/login", "/user/createUser","/swagger-resources/**", "/webjars/**", "/swagger-ui.html/**",
                        "/api", "/api-docs", "/api-docs/**", "/service-worker.js/**", "/doc.html/**", "/favicon.ico");
        //写好的拦截器需要通过这里添加注册才能生效
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
