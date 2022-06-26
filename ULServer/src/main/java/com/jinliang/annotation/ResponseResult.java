package com.jinliang.annotation;

import java.lang.annotation.*;

/**
 * @author yejinliang
 * @create 2022-06-26 17:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ResponseResult {

}
