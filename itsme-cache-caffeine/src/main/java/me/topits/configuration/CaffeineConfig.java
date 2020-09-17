package me.topits.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author QingKe
 * @date 2020-09-17 12:28
 **/
@EnableCaching
@Configuration
public class CaffeineConfig {

    @Value("${core.cache.caffeine.expire.after.time:3}")
    public Integer caffeineExpireAfterTime;
    @Value("${core.cache.caffeine.maximum.size:10000}")
    public Integer caffeineMaximumSize;

    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(caffeineExpireAfterTime, TimeUnit.SECONDS)
                .maximumSize(caffeineMaximumSize)
                .recordStats();
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }


}
