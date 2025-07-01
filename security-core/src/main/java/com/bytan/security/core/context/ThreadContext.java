package com.bytan.security.core.context;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程上下文
 * @Author：ByTan
 * @Email：tx1611235218@gmail.com
 * @Date：2025/2/11 11:52
 */
public class ThreadContext {

    private static final ThreadLocal<Map<String, Object>> ThreadLocal = new ThreadLocal<>();

    protected ThreadContext() {}

    /**
     * 设置上下文线程存储变量
     * @param key 键
     * @param value 值
     */
    public static void set(String key, Object value) {
        Map<String, Object> map = getThreadLocalMap();
        map.put(key, value == null ? "" : value);
    }

    /**
     * 获取上下文线程存储变量
     * @param key 键
     * @return 值
     */
    @SuppressWarnings(value = { "unchecked"})
    public static <T> T get(String key) {
        Map<String, Object> map = getThreadLocalMap();
        Object value = map.get(key);
        return value == null ? null : (T) value;
    }

    /**
     * 获取上下文线程存储变量
     * @return 线程存储变量
     */
    private static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = ThreadLocal.get();
        if (map == null) {
            map = new ConcurrentHashMap<String, Object>();
            ThreadLocal.set(map);
        }
        return map;
    }

    /**
     * 清空上下文线程存储变量
     */
    public static void clean() {
        ThreadLocal.remove();
    }

}
