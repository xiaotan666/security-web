package com.bytan.security.core.data.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO 待完善基于本地内存的dao存储方式
*@Author：ByTan
*@Email：tx1611235218@gmail.com
*@Date：2025/1/18  15:41
*/
@SuppressWarnings(value = { "unchecked" })
public class DefaultMemoryDao extends HashMap<String, Object> implements SecurityDao {

    @Override
    public void save(String key, Object value) {
        put(key, value);
    }

    @Override
    public void saveBatchByMap(String key, Map<String, Object> objMap) {
        put(key, objMap);
    }

    @Override
    public void delete(String key) {
        remove(key);
    }

    @Override
    public void deleteByMap(String key, String field) {
        Map<Object, Object> map = new HashMap<>(get(key));
        map.remove(field);
        put(key, map);
    }

    @Override
    public void update(String key, Object value) {
        put(key, value);
    }

    @Override
    public void updateBatchByMap(String key, Map<String, Object> objMap) {
        put(key, objMap);
    }

    @Override
    public <T> T get(String key) {
        Object value = super.get(key);
        return value == null ? null : (T) value;
    }

    @Override
    public <T> T getByMap(String key, String field) {
        return get(key);
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
