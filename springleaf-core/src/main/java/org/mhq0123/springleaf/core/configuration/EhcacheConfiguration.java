package org.mhq0123.springleaf.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/12.
 * desc:    ehcache缓存配置
 */
@EnableCaching//标注启动缓存.
public class EhcacheConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(EhcacheConfiguration.class);

    @PostConstruct
    public void init() {
        logger.info(">>>>>>>>>>>>>>EhcacheConfiguration started ...");
    }

    /**
     * ehcache 主要的管理器
     * @param bean
     * @return
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
       logger.info(">>>>>>>>>>>>>>ehCacheCacheManager init success");
       return new EhCacheCacheManager(bean.getObject());
    }
   
    /*
       * 据shared与否的设置,
       * Spring分别通过CacheManager.create()
       * 或new CacheManager()方式来创建一个ehcache基地.
       *
       * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
       *
       */
      @Bean
      public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        logger.info(">>>>>>>>>>>>>>ehCacheManagerFactoryBean init start...");
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        logger.info(">>>>>>>>>>>>>>ehCacheManagerFactoryBean init success...");
        return cacheManagerFactoryBean;
      }
   
}