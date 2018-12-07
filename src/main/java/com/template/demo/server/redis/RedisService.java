package com.template.demo.server.redis;

/**
 * @Title: RedisService
 * @Description: Redis接口
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 15:35
 */
public interface RedisService {
    /**
     * 获取Object类型的值
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 插入Object类型的值
     * @param key
     * @param value
     */
    public void set(String key, Object value);

    /**
     * 带过期时间
     * @param key
     * @param value
     * @param seconds 过期时间/秒
     */
    public void set(String key, Object value, long seconds);

    /**
     * 获取 String 类型的值
     * @param key
     * @return
     */
    String getStr(String key);

    /**
     * 插入String类型的值
     * @param key
     * @param value
     */
    void setStr(String key, String value);

    /**
     * 插入String类型的值带过期时间
     * @param key
     * @param value
     * @param seconds
     */
    void setStr(String key, String value, long seconds);

    /**
     * 值自增
     * @param key
     * @param value
     */
    void increment(String key, long value, long seconds);

    /**
     * 删除
     * @param key
     */
    Boolean delete(String key);
}
