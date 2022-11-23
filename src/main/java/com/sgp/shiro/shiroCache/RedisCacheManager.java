package com.sgp.shiro.shiroCache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager extends AbstractCacheManager {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected Cache createCache(String s) throws CacheException {
        return redisCache;
    }
}
