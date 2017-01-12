package org.mhq0123.springleaf.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName ThreadLocalUtils
 * @date 2016-07-06
 * @memo 线程变量缓存
 */
public class ThreadLocalUtils {
    private ThreadLocalUtils(){}

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * put数据
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            threadLocal.set(new HashMap<String, Object>());
        }
        threadLocal.get().put(key, value);
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public static Object get(String key) {
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        return data.get(key);
    }

    /**
     * 利用泛型提前强转类型的get方法
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        return (T)data.get(key);
    }

    /**
     * 移除单个数据
     * @param key
     * @return
     */
    public static Object remove(String key) {
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        return data.remove(key);
    }

    /**
     * 清除所有数据
     */
    public static void removeAll() {
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return;
        }
        data.clear();
        threadLocal.remove();
    }

}
