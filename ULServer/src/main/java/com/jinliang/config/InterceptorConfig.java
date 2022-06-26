package com.jinliang.config;

import com.jinliang.interceptor.UserLoginInterceptor;
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
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error", "/user/**", "/categories", "/products", "/products/*", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                        "/api", "/api-docs", "/api-docs/**", "/service-worker.js/**", "/doc.html/**");
        //写好的拦截器需要通过这里添加注册才能生效
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
