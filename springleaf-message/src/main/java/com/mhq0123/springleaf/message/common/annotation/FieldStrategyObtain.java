package com.mhq0123.springleaf.message.common.annotation;

import com.mhq0123.springleaf.message.common.type.FieldStrategyObtainType;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldStrategyObtain
 * @date 2016-07-05
 * @memo 字段解析规则来源 注解/DB
 */
public @interface FieldStrategyObtain {
    /**
     * 解析来源类型
     * @return
     */
    public FieldStrategyObtainType obtainType();
}
