package com.jinliang.common.cache;

import com.jinliang.common.util.RedisUtils;
import org.apache.ibatis.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * 自定义redis缓存实现
 *
 * @author yejinliang
 * @create 2022-07-03 4:38
 */
public class RedisCache implements Cache {
    private final String id;

    // 必须存在的构造方法
    public RedisCache(String id) {
        this.id = id;
    }


    /**
     * 返回cache唯一标识
     *
     * @return String
     */
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        // 使用redis的hash类型作为缓存存储模型  key  hashkey  value
        RedisUtils.putObjectForHash(id, key, value);
    }

    @Override
    public Object getObject(Object key) {
        // 使用redis的hash类型取值
        return RedisUtils.getObjectForHash(id, key);
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        RedisUtils.delete(id, false);
    }

    @Override
    public int getSize() {
        // 获取hash中的key value数量
        return RedisUtils.getHashSize(id);
    }


}
