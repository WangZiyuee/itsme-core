package me.topits.service;

/**
 * @author QingKe
 * @date 2020-08-28 21:33
 **/
public interface IDistributedLock {

    /**
     * 获取锁
     *
     * @param key       key
     * @param leaseTime 获取锁使用时间
     * @param waitTime  等待获取锁时间
     * @return 锁
     */
    Object tryLock(String key, long leaseTime, long waitTime);

    /**
     * 释放锁
     *
     * @param lock 锁
     */
    void unLock(Object lock);
}
