package me.topits.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author QingKe
 * @date 2020-09-11 17:44
 **/
public interface IRedisCache {

    /**
     * 存在key
     *
     * @param key key
     * @return 存在true
     */
    Boolean hasKey(String key);

    /**
     * 设置过期时间
     *
     * @param key     key
     * @param timeout 时间
     * @param unit    单位
     * @return 结果
     */
    Boolean expire(String key, long timeout, TimeUnit unit);

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param date 过期日期
     * @return 结果
     */
    Boolean expireAt(String key, Date date);

    /**
     * 获取过期时间
     *
     * @param key key
     * @return 过期时间 秒
     */
    Long getExpire(String key);

    /**
     * 匹配key
     *
     * @param pattern key正则
     * @return key集合
     */
    Set<String> keys(String pattern);

    /**
     * 设置字符value
     *
     * @param key   key
     * @param value value
     */
    void setString(String key, String value);

    /**
     * 获取字符value
     *
     * @param key key
     * @return value
     */
    String getString(String key);

    /**
     * 设置新的value 并获取原有value
     *
     * @param key   key
     * @param value new value
     * @return old value
     */
    String getAndSetString(String key, String value);

    /**
     * 批量获取
     *
     * @param keys keys
     * @return List<String>
     */
    List<String> multiGet(Collection<String> keys);

    /**
     * 批量添加
     *
     * @param maps maps
     */
    void multiSet(Map<String, String> maps);

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   key
     * @param value value
     * @return 之前已经存在返回false, 不存在返回true
     */
    Boolean setIfAbsent(String key, String value);

    /**
     * 自增或自减
     *
     * @param key       key
     * @param delta 数
     * @return 结果
     */
    Long increment(String key, long delta);

}
