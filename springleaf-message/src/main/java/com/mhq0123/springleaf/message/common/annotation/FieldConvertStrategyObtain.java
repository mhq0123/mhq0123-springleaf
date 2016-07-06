package com.mhq0123.springleaf.message.common.annotation;

import com.mhq0123.springleaf.message.common.type.FieldConvertStrategyObtainType;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldConvertStrategyObtain
 * @date 2016-07-05
 * @memo 字段转换策略来源 注解/DB
 */
public @interface FieldConvertStrategyObtain {
    /**
     * 解析来源类型
     * @return
     */
    public FieldConvertStrategyObtainType obtainType();
}
