package org.mhq0123.springleaf.message.common.annotation;

import org.mhq0123.springleaf.message.common.interceptor.FieldConvertInterceptorInterface;
import org.mhq0123.springleaf.message.common.type.FieldConvertInterceptorAction;
import org.mhq0123.springleaf.message.common.type.FieldConvertInterceptorTime;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldConvertEnhance
 * @date 2016-07-05
 * @memo 字段增强处理
 */
public @interface FieldConvertEnhance {
    /**
     * 拦截时机
     * @return
     */
    public FieldConvertInterceptorTime time();

    /**
     * 拦截动作
     * @return
     */
    public FieldConvertInterceptorAction action();

    /**
     * 拦截器
     * @return
     */
    public Class<? extends FieldConvertInterceptorInterface> interceptor();
}
