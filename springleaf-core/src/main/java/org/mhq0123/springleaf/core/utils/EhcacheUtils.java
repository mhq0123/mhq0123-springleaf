package org.mhq0123.springleaf.core.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SpringUtils
 * @date 2016-07-06
 * @memo
 */
public class EhcacheUtils {
  
    private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);
  
    private static EhcacheUtils instance;

    private EhcacheUtils() {}

    /**
     * 创建
     * @return
     */
    public static EhcacheUtils instance() {
        //懒汉式
        if(instance == null){
            synchronized (EhcacheUtils.class) {
                if(instance == null){//二次检查
                    instance = new EhcacheUtils();
                }
            }
        }
        return instance;
    }

    /**
     * put 存值
     * @param cacheName
     * @param key
     * @param value
     */
    public void cachePut(String cacheName, String key, Object value) {
        cacheManager.getCache(cacheName).put(new Element(key, value));
    }

    /**
     * 获取单个
     * @param cacheName
     * @param key
     * @return
     */
    public Object cacheGet(String cacheName, String key) {
        Element element = cacheManager.getCache(cacheName).get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 获取一组
     * @param cacheName
     * @return
     */
    public Cache cacheGet(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    /**
     * 删除单个
     * @param cacheName
     * @param key
     */
    public void cacheEvict(String cacheName, String key) {
        cacheManager.getCache(cacheName).remove(key);
    }

    public void cacheEvict(String cacheName) {
        cacheManager.getCache(cacheName).removeAll();
    }
  
}  