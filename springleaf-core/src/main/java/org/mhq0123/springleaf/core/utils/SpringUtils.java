package org.mhq0123.springleaf.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName SpringUtils
 * @date 2016-07-06
 * @memo
 */
@Repository
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 获取容器
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名称获取bean
     * @param name
     * @return
     * @throws BeansException
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据class 获取bean
     * @param cls
     * @return
     */
    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
}
