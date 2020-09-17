package me.topits.service;

/**
 * @author QingKe
 * @date 2020-09-17 15:12
 **/
public interface ICaffeineCache {

    /**
     * 获取value
     *
     * @param key key
     * @param <T> T
     * @return value
     */
    <T> T getValue(String key);

    /**
     * 获取value
     *
     * @param cacheName cacheName
     * @param key       key
     * @param <T>       T
     * @return value
     */
    <T> T getValue(String cacheName, String key);

    /**
     * 设置key:value
     *
     * @param key   key
     * @param value value
     */
    void setValue(String key, Object value);

    /**
     * 设置key:value
     *
     * @param cacheName cacheName
     * @param key       key
     * @param value     value
     */
    void serValue(String cacheName, String key, Object value);
}
