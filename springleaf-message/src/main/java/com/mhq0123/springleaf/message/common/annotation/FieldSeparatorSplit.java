package com.mhq0123.springleaf.message.common.annotation;

import com.mhq0123.springleaf.message.common.type.FieldPadType;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldSeparatorSplit
 * @date 2016-07-05
 * @memo 字段分隔截取
 */
public @interface FieldSeparatorSplit {
    /**
     * 栏位名称
     * @return
     */
    public String name();

    /**
     * 栏位代码
     * @return
     */
    public String fieldCode() default "";

    /**
     * 分隔符
     * @return
     */
    public String separator() default ",";

    /**
     * 分割序号 从0开始
     * @return
     */
    public int index();

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
