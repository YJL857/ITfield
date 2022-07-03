package com.jinliang.mapper;

import com.jinliang.common.cache.RedisCache;
import com.jinliang.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yejinliang
 * @since 2022-06-26
 */
@Mapper
@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class) // 开启二级缓存
@CacheNamespaceRef(UserInfoMapper.class)
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
