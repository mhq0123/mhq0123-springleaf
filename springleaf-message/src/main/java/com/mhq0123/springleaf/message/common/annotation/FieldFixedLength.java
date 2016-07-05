package com.mhq0123.springleaf.message.common.annotation;

import com.mhq0123.springleaf.message.common.type.FieldPadType;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldFixedLength
 * @date 2016-07-05
 * @memo 字段定长
 */
public @interface FieldFixedLength {
    /**
     * 栏位名称
     * @return
     */
    public String name() default "";

    /**
     * 栏位代码
     * @return
     */
    public String fieldCode() default "";

    /**
     * 开始位置
     * @return
     */
    public int startPos() default 0;

    /**
     * 长度
     * @return
     */
    public int length() default 0;

    /**
     * 填充方向
     * @return
     */
    public FieldPadType padType() default FieldPadType.RIGHT;

    /**
     * 填充
     * @return
     */
    public char material() default ' ';

    /**
     * 数据类型
     * @return
     */
    public Class<?> fieldClass() default String.class;

    /**
     * 匹配格式
     * @return
     */
    public String regex() default "";
}
