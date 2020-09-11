package me.topits.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.topits.service.IRedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author QingKe
 * @date 2020-09-11 17:45
 **/
@Slf4j
@Service
public class RedisCacheServiceImpl implements IRedisCache {

    final
    StringRedisTemplate redisTemplate;

    public RedisCacheServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getAndSetString(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }


    @Override
    public Boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // TODO: 2020-09-11 完善
}
