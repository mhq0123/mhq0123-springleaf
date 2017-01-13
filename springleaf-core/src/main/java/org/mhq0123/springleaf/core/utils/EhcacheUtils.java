package org.mhq0123.springleaf.core.utils;

import com.alibaba.fastjson.JSONObject;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SpringUtils
 * @date 2016-07-06
 * @memo
 */
public class EhcacheUtils {

    private static final Logger logger = LoggerFactory.getLogger(EhcacheUtils.class);
  
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
                    logger.info(">>>>>>>>>>>>>>instance success...");
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
        logger.info(">>>>>>>>>>>>>>cachePut cacheName:{},key:{},value:{}", cacheName, key, JSONObject.toJSONString(value, true));
        cacheManager.getCache(cacheName).put(new Element(key, value));
    }

    /**
     * 获取单个
     * @param cacheName
     * @param key
     * @return
     */
    public Object cacheGet(String cacheName, String key) {
        logger.info(">>>>>>>>>>>>>>cacheGet cacheName:{},key:{}", cacheName, key);
        Element element = cacheManager.getCache(cacheName).get(key);
        Object value = element == null ? null : element.getObjectValue();
        logger.info(">>>>>>>>>>>>>>cacheGet value:{}", JSONObject.toJSONString(value, true));
        return value;
    }

    /**
     * 获取一组
     * @param cacheName
     * @return
     */
    public Cache cacheGet(String cacheName) {
        logger.info(">>>>>>>>>>>>>>cacheGet cacheName:{}", cacheName);
        return cacheManager.getCache(cacheName);
    }

    /**
     * 删除单个
     * @param cacheName
     * @param key
     */
    public void cacheEvict(String cacheName, String key) {
        logger.info(">>>>>>>>>>>>>>cacheEvict cacheName:{}, key:{}", cacheName, key);
        cacheManager.getCache(cacheName).remove(key);
        logger.info(">>>>>>>>>>>>>>cacheEvict success...");
    }

    public void cacheEvict(String cacheName) {
        logger.info(">>>>>>>>>>>>>>cacheEvict cacheName:{}", cacheName);
        cacheManager.getCache(cacheName).removeAll();
        logger.info(">>>>>>>>>>>>>>cacheEvict success...");
    }
  
}  