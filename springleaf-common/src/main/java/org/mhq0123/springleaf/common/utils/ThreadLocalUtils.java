package org.mhq0123.springleaf.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalUtils.class);

    private ThreadLocalUtils(){}

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * put数据
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        logger.info(">>>>>>>>>>>>>>put key:{}, value:{}", key, JSONObject.toJSONString(value, true));
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
        logger.info(">>>>>>>>>>>>>>get key:{}", key);
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        Object value = data.get(key);
        logger.info(">>>>>>>>>>>>>>get value:{}", JSONObject.toJSONString(value, true));
        return value;
    }

    /**
     * 利用泛型提前强转类型的get方法
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        logger.info(">>>>>>>>>>>>>>get key:{}, clazz:{}", key, clazz);
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        T value = (T)data.get(key);
        logger.info(">>>>>>>>>>>>>>get value:{}", JSONObject.toJSONString(value, true));
        return value;
    }

    /**
     * 移除单个数据
     * @param key
     * @return
     */
    public static Object remove(String key) {
        logger.info(">>>>>>>>>>>>>>remove key:{}", key);
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return null;
        }
        Object value = data.remove(key);
        logger.info(">>>>>>>>>>>>>>remove value:{}", JSONObject.toJSONString(value, true));
        return value;
    }

    /**
     * 清除所有数据
     */
    public static void removeAll() {
        logger.info(">>>>>>>>>>>>>>removeAll start...");
        Map<String, Object> data = threadLocal.get();
        if(null == data) {
            return;
        }
        data.clear();
        threadLocal.remove();
        logger.info(">>>>>>>>>>>>>>removeAll end...");
    }

}
