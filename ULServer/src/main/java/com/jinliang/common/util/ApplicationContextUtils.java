package com.jinliang.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 用来获取springboot创建好的工厂
 *
 * @author yejinliang
 * @create 2022-07-03 5:19
 */
@Configuration
public class ApplicationContextUtils implements ApplicationContextAware {

    // 保留下来的工厂
    private static ApplicationContext applicationContext;

    // 将创建好的工厂以参数的形式传递给这个类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextUtils.applicationContext == null){
            ApplicationContextUtils.applicationContext  = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 提供在工厂中获取对象的方法
    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }
}
