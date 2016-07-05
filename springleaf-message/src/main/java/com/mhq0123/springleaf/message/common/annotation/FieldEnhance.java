package com.mhq0123.springleaf.message.common.annotation;

import com.mhq0123.springleaf.message.common.interceptor.FieldInterceptorInterface;
import com.mhq0123.springleaf.message.common.type.FieldInterceptorAction;
import com.mhq0123.springleaf.message.common.type.FieldInterceptorTime;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldEnhance
 * @date 2016-07-05
 * @memo 字段增强处理
 */
public @interface FieldEnhance {
    /**
     * 拦截时机
     * @return
     */
    public FieldInterceptorTime time();

    /**
     * 拦截动作
     * @return
     */
    public FieldInterceptorAction action();

    /**
     * 拦截器
     * @return
     */
    public Class<? extends FieldInterceptorInterface> interceptor();
}
