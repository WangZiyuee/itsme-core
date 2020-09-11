package me.topits.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.topits.service.IDistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author QingKe
 * @date 2020-08-28 21:33
 **/
@Slf4j
@Service
public class DistributedLockImpl implements IDistributedLock {

    final
    RedissonClient redissonClient;

    /** LOCK前缀 */
    private static final String LOCK_KEY_PREFIX = "lock:";

    public DistributedLockImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Object tryLock(String key, long waitTime, long leaseTime) {
        try {
            RLock lock = redissonClient.getLock(LOCK_KEY_PREFIX + key);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS) ? lock : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    @Override
    public void unLock(Object lock) {
        try {
            if (lock != null) {
                ((RLock) lock).unlock();
            }
        } catch (Exception ignored) {
        }
    }


}
