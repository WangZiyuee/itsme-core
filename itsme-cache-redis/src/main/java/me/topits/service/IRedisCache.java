package me.topits.service;

import java.time.LocalDateTime;
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
     * 删除key
     *
     * @param key key
     * @return 结果
     */
    Boolean delete(String key);

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
     * 设置过期时间
     *
     * @param key           key
     * @param localDateTime localDateTime
     * @return 结果
     */
    Boolean expireAt(String key, LocalDateTime localDateTime);

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
     * 设置字符value
     *
     * @param key     key
     * @param value   value
     * @param timeout timeout
     * @param unit    unit
     */
    void setString(String key, String value, long timeout, TimeUnit unit);

    /**
     * 设置字符value
     *
     * @param key   key
     * @param value value
     * @param date  date
     */
    void setString(String key, String value, Date date);

    /**
     * 设置字符value
     *
     * @param key           key
     * @param value         value
     * @param localDateTime date
     */
    void setString(String key, String value, LocalDateTime localDateTime);

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
     * @param key   key
     * @param delta 数
     * @return 结果
     */
    Long increment(String key, long delta);

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key      key
     * @param hashKeys hashKeys
     * @return value
     */
    Object hGet(String key, String hashKeys);

    /**
     * 哈希表存储
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     */
    void hPush(String key, String hashKey, Object value);

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     * @return 结果
     */
    Boolean hPutIfAbsent(String key, String hashKey, Object value);

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key      key
     * @param hashKeys hashKeys
     */
    void hDelete(String key, Object... hashKeys);

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @return Set<Object>
     */
    Set<Object> hKeys(String key);

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   key
     * @param index index
     * @return 元素
     */
    String lIndex(String key, long index);

    /**
     * 存储在list头部
     *
     * @param key   key
     * @param value value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long lLeftPush(String key, String value);

    /**
     * 当list存在的时候才加入
     *
     * @param key   key
     * @param value value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long lLeftPushIfPresent(String key, String value);

    /**
     * 存储在list尾部
     *
     * @param key   key
     * @param value value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long lRightPush(String key, String value);

    /**
     * 当list存在的时候才加入
     *
     * @param key   key
     * @param value value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long lRightPushIfPresent(String key, String value);

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   key
     * @param index index
     * @param value value
     */
    void lSet(String key, long index, String value);

    /**
     * 删除集合中值等于value得元素
     *
     * @param key   key
     *              index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param count count
     * @param value value
     * @return {@literal null} when used in pipeline / transaction.
     */
    Long lRemove(String key, long count, String value);
}
