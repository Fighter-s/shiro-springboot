package com.sgp.shiro.shiroCache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisCache<K,V> implements Cache<K, V> {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SHRIO_CACHE_PREFIX = "shiro_cache_prefix_";

    @Override
    public V get(K k) throws CacheException {
        log.info("从缓存中获取授权信息");
        redisTemplate.expire(SHRIO_CACHE_PREFIX+k,15, TimeUnit.MINUTES);
        return (V) redisTemplate.opsForValue().get(SHRIO_CACHE_PREFIX + k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(SHRIO_CACHE_PREFIX+k,v,15,TimeUnit.MINUTES);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = (V)redisTemplate.opsForValue().get(SHRIO_CACHE_PREFIX+k);
        redisTemplate.delete(SHRIO_CACHE_PREFIX+k);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        Set keys = redisTemplate.keys(SHRIO_CACHE_PREFIX + "*");
        redisTemplate.delete(keys);
    }

    @Override
    public int size() {
        Set keys = redisTemplate.keys(SHRIO_CACHE_PREFIX + "*");
        return keys.size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(SHRIO_CACHE_PREFIX + "*");
    }

    @Override
    public Collection<V> values() {
        Set keys = redisTemplate.keys(SHRIO_CACHE_PREFIX + "*");
        Set<V> set= new HashSet<>();
        for (Object key : keys) {
            V v = (V)redisTemplate.opsForValue().get(SHRIO_CACHE_PREFIX+key);
            set.add(v);
        }
        return set;
    }
}
