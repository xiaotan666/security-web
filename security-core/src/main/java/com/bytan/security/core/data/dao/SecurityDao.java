package com.bytan.security.core.data.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 数据存储方式接口定义
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/1/17 11:58
 */
public interface SecurityDao {

    /**
     * 永不过期
     */
    long NOT_EXPIRE = -1;

    /**
     * 保存数据
     * @param key 键
     * @param value 值
     */
    void save(String key, Object value);

    /**
     * 保存Map格式的数据
     * @param key 键
     * @param field 字段
     * @param obj 值
     */
    default void saveByMap(String key, String field, Object obj) {
        saveBatchByMap(key, new HashMap<String, Object>() {{
            put(field, obj);
        }});
    }

    /**
     * 批量保存Map格式的数据
     * @param key 键
     * @param objMap 值
     */
    void saveBatchByMap(String key, Map<String, Object> objMap);

    /**
     * 删除数据
     * @param key 键
     */
    void delete(String key);

    /**
     * 删除Map里面某一个字段的数据
     * @param key 键
     * @param field 映射字段
     */
    void deleteByMap(String key, String field);

    /**
     * 更新数据
     * @param key 键
     * @param value 值
     */
    void update(String key, Object value);

    /**
     * 更新Map格式的数据
     * @param key 键
     * @param field 字段
     * @param obj 值
     */
    default void updateByMap(String key, String field, Object obj) {
        updateBatchByMap(key, new HashMap<String, Object>() {{
            put(field, obj);
        }});
    }

    /**
     * 批量更新Map里面某一个字段的数据
     * @param key 键
     * @param objMap 值
     */
    void updateBatchByMap(String key, Map<String, Object> objMap);

    /**
     * 获取数据
     * @param key 键
     * @return 数据信息
     */
    <T> T get(String key);

    /**
     * 获取Map里面某一个字段的数据
     * @param key 键
     * @param field 映射字段
     * @return 数据信息
     */
    <T> T getByMap(String key, String field);

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    void expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 设置过期时间（默认秒数）
     * @param key 键
     * @param timeout 过期时间
     */
    default void expire(String key, long timeout) {
        expire(key, timeout, TimeUnit.SECONDS);
    }
}
