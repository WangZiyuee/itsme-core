package me.topits.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.topits.service.ICaffeineCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author QingKe
 * @date 2020-09-17 15:12
 **/
@Slf4j
@Service
@SuppressWarnings("ALL")
public class CaffeineCacheImpl implements ICaffeineCache {

    private static final String DEFAULT_CACHE = "default";

    @Autowired
    CacheManager caffeineCacheManager;

    @Override
    public <T> T getValue(String key) {
        Assert.hasText(key, "Cache Key is Blank");
        Cache cache = caffeineCacheManager.getCache(DEFAULT_CACHE);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return (T) valueWrapper.get();
            }
        }
        return null;
    }

    @Override
    public <T> T getValue(String cacheName, String key) {
        Assert.hasText(cacheName, "Cache Name is Blank");
        Assert.hasText(key, "Cache Key is Blank");
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return (T) valueWrapper.get();
            }
        }
        return null;
    }

    @Override
    public void setValue(String key, Object value) {
        Assert.hasText(key, "Cache Key is Blank");
        Cache cache = caffeineCacheManager.getCache(DEFAULT_CACHE);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    @Override
    public void serValue(String cacheName, String key, Object value) {
        Assert.hasText(cacheName, "Cache Name is Blank");
        Assert.hasText(key, "Cache Key is Blank");
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }
}
