package com.bytan.security.core.data.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于本地内存实现的存储方式
* @Author：ByTan
* @Email：tx1611235218@gmail.com
* @Date：2025/1/18  15:41
*/
public class DefaultMemoryDao implements SecurityDao {

    private final Cache<String, Object> cache = Caffeine.newBuilder().build();

    @Override
    public void save(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void saveBatchByMap(String key, Map<String, Object> objMap) {
        cache.put(key, objMap);
    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }

    @Override
    public void deleteByMap(String key, String field) {
        Map<Object, Object> map = new HashMap<>(get(key));
        map.remove(field);
        cache.put(key, map);
    }

    @Override
    public void update(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void updateBatchByMap(String key, Map<String, Object> objMap) {
        cache.put(key, objMap);
    }

    @SuppressWarnings(value = {"unchecked"})
    @Override
    public <T> T get(String key) {
        Object value = cache.getIfPresent(key);
        return value == null ? null : (T) value;
    }

    @SuppressWarnings(value = {"unchecked"})
    @Override
    public <T> T getByMap(String key, String field) {
        Object mapValue = get(key);
        if (mapValue != null) {
            Map<String, Object> map = (Map<String, Object>) mapValue;
            Object value = map.getOrDefault(field, null);
            return value == null ? null : (T) value;
        }

        return null;
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        if (timeout != SecurityDao.NOT_EXPIRE) {
            cache.policy()
                    .expireVariably()
                    .ifPresent(varExp -> varExp.setExpiresAfter(key, timeout, timeUnit)
                    );
        }
    }
}
