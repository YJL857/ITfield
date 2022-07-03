package com.jinliang.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author yejinliang
 * @create 2022-07-03 17:51
 */
@Component
public class RedisUtils {

    /**
     * get Key_value
     *
     * @param key md5加密
     * @return Object
     */
    public static Object getObjectForValue(Object key){
        return getRedisTemplate().opsForValue().get(getKeyToMd5(key.toString()));
    }

    /**
     * get Hash Key_value
     *
     * @param id id
     * @param key md5加密
     * @return Object
     */
    public static Object getObjectForHash(Object id, Object key){
        return getRedisTemplate().opsForHash().get(id.toString(),getKeyToMd5(key.toString()));
    }

    /**
     * key value
     *
     * @param key md5加密
     * @param value value
     */
    public static void putObjectForValue(Object key, Object value){
        getRedisTemplate().opsForValue().set(getKeyToMd5(key.toString()),value);
    }

    /**
     * Key_value 加过期时间
     * @param key md5加密
     * @param value value
     * @param time 过期时间
     * @param timeUnit 时间类型
     */
    public static void putObjectForValue(Object key, Object value, Long time, TimeUnit timeUnit){
        getRedisTemplate().opsForValue().set(getKeyToMd5(key.toString()),value,time, timeUnit);
    }


    /**
     * Hash Key_value
     * @param id id
     * @param key md5加密
     * @param value value
     */
    public static void putObjectForHash(Object id, Object key, Object value){
        getRedisTemplate().opsForHash().put(id.toString(),getKeyToMd5(key.toString()),value);
    }

    /**
     * Hash Key_value 设置缓存时间(Hash的缓存过期时间之针对最外面的id生效)
     *
     * @param id id
     * @param key md5加密
     * @param value value
     * @param time 过期时间
     * @param timeUnit 时间类型
     */
    public static void putObjectForHash(Object id, Object key, Object value, Long time, TimeUnit timeUnit){
        putObjectForHash(id,key,value);
        getRedisTemplate().expire(id.toString(),time,timeUnit);
    }

    /**
     * delete
     * @param key key
     * @param isMd5 是否对key进行MD5加密，再进行删除
     */
    public static void delete(Object key,boolean isMd5){
        if (isMd5) {
            getRedisTemplate().delete(getKeyToMd5(key.toString()));
        } else {
            getRedisTemplate().delete(key.toString());
        }
    }

    public static int getValueSize(Object key) {
        return getRedisTemplate().opsForValue().size(getKeyToMd5(key.toString())).intValue();
    }

    public static int getHashSize(Object id){
        return getRedisTemplate().opsForHash().size(id.toString()).intValue();
    }

    /**
     * 对key进行MD5加密
     *
     * @param key String
     * @return String
     */
    public static String getKeyToMd5(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 封装RedisTemplate
     *
     * @return RedisTemplate
     */
    private static RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // String的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
