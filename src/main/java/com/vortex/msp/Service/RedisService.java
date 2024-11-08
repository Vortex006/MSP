package com.vortex.msp.Service;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisCallback;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    /**
     * 保存key-value
     *
     * @param key   指定的key
     * @param value 指定的value
     */
    Boolean setString(String key, Object value);


    /**
     * 保存key-value
     *
     * @param key      指定的key
     * @param value    指定的value
     * @param time     失效时间
     * @param timeUnit 时间单位
     */
    Boolean setString(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 保存hash集合
     *
     * @param key   指定的key
     * @param field 指定的field
     * @param value 指定的value
     * @return 保存成功返回true，否则返回false
     */
    Boolean setHash(String key, String field, Object value);

    /**
     * 保存hash集合
     *
     * @param key 指定的key
     * @param map 指定的hash集合
     * @return 保存成功返回true，否则返回false
     */
    Boolean setHash(String key, HashMap<String, Object> map);

    /**
     * 保存list集合
     *
     * @param key   指定的key
     * @param value 指定的value
     * @return 保存成功返回true，否则返回false
     */
    Boolean setList(String key, Object value);

    /**
     * 保存list集合
     *
     * @param key  指定的key
     * @param list 指定的list集合
     * @return 保存成功返回true，否则返回false
     */
    Boolean setList(String key, List<Object> list);

    /**
     * 保存list集合
     *
     * @param key   指定的key
     * @param value 指定的value
     * @return 保存成功返回true，否则返回false
     */
    Boolean setListLeft(String key, Object value);

    /**
     * 保存list集合
     *
     * @param key  指定的key
     * @param list 指定的list集合
     * @return 保存成功返回true，否则返回false
     */
    Boolean setListLeft(String key, List<Object> list);

    /**
     * 根据key获取value
     *
     * @param key 指定的key
     * @return 指定的key对应的value
     */
    Object getString(String key);

    /**
     * 获取hash集合中的某个值
     *
     * @param key   指定的key
     * @param field 指定的field
     * @return 指定的key对应的field对应的value
     */
    Object getHash(String key, String field);

    /**
     * 获取hash集合
     *
     * @param key 指定的key
     * @return 指定的key对应的hash集合
     */
    HashMap<String, Object> getHash(String key);

    /**
     * 获取list集合
     *
     * @param key   指定的key
     * @param start 开始位置
     * @param end   结束位置
     * @return 指定的key对应的list集合
     */
    List<Object> getList(String key, long start, long end);

    /**
     * 获取list集合的长度
     *
     * @param key 指定的key
     * @return list集合的长度
     */
    Long getListSize(String key);

    /**
     * 删除key
     *
     * @param key 指定的key
     */
    Boolean delete(String key);

    /**
     * 删除多个key
     *
     * @param keys 指定的key集合
     */
    Boolean delete(List<String> keys);

    /**
     * 指定key的失效时间 默认单位：秒 (s)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expire(String key, long time);

    /**
     * 指定key的失效时间
     *
     * @param key      指定的key
     * @param time     失效时间
     * @param timeUnit 时间单位
     * @return 是否成功设置
     */
    Boolean expire(String key, long time, TimeUnit timeUnit);

    /**
     * 指定key的失效时间 默认单位：毫秒 (ms)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expireMilliSeconds(String key, long time);

    /**
     * 指定key的失效时间 默认单位：秒 (s)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expireSeconds(String key, long time);

    /**
     * 指定key的失效时间 默认单位：分钟 (m)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expireMinutes(String key, long time);

    /**
     * 指定key的失效时间 默认单位：小时 (h)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expireHours(String key, long time);

    /**
     * 指定key的失效时间 默认单位：天 (d)
     *
     * @param key  指定的key
     * @param time 失效时间
     * @return 是否成功设置
     */
    Boolean expireDays(String key, long time);

    /**
     * 指定key的失效时间
     *
     * @param key  指定的key
     * @param date 失效日期
     */
    Boolean expire(String key, LocalDate date);

    /**
     * 根据key获取过期时间
     *
     * @param key 指定的key
     * @return 指定的key的过期时间 单位: 毫秒(ms)
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 指定的key
     * @return 指定的key是否存在 true:存在 false:不存在
     */
    Boolean hasKey(String key);

    /**
     * 让指定的key递增
     *
     * @param key   指定的key
     * @param delta 递增的值
     * @return 递增后的值
     */
    Long valueAdd(String key, long delta);

    /**
     * 让指定的key递减
     *
     * @param key   指定的key
     * @param delta 递减的值
     * @return 递减后的值
     */
    Long valueRemove(String key, long delta);

    /**
     * 将map转换为hashMap
     *
     * @param map 指定的map
     * @return 指定的map对应的hashMap
     */
    HashMap<String, Object> changeMap(Map<Object, Object> map);

    /**
     * 判断key对应的value是否等于value
     *
     * @param key   指定的key
     * @param value 需要比较的Value
     */
    Boolean equals(String key, Object value);

    /**
     * 执行自定义的Redis命令
     *
     * @param action 自定义的Redis命令
     */
    T execute(RedisCallback<T> action);

}