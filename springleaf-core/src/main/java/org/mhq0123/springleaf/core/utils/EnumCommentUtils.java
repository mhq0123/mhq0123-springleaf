package org.mhq0123.springleaf.core.utils;

import org.mhq0123.springleaf.core.annotation.Comment;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName EnumCommentUtils
 * @date 2016-07-06
 * @memo comment注解工具类
 */
public class EnumCommentUtils {
    /**
     * 根据枚举类型 及code值获取常量
     * @param clzz
     * @param code
     * @return
     */
    public static <E extends Enum<E>> E valueof(Class<E> clzz, String code) {

        if(StringUtils.isEmpty(code)) {
            throw new NullPointerException("code不能为空");
        }
        try {
            // 循环判断
            List<E> enumList = (List<E>) EnumUtils.getEnumList(clzz);
            if(null != enumList && !enumList.isEmpty()) {
                for(E field : enumList) {
                    // code相等，则返回
                    if(code.equals(field.getClass().getField(field.name()).getAnnotation(Comment.class).code())) {
                        return field;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("运行异常："+e.getMessage());

        }
        throw new IllegalArgumentException("传入的[class:"+clzz+",code:"+code+"]获取常量为空");
    }


    /**
     * 获取code值
     * @param column
     * @return
     */
    public static <E extends Enum<E>> String getCode(Enum<E> column) {
        if(null == column) {
            throw new IllegalArgumentException("传入的column为空");
        }
        try {
            return column.getClass().getField(column.name()).getAnnotation(Comment.class).code();

        } catch (Exception e) {

            throw new RuntimeException("运行异常："+e.getMessage());
        }
    }

    /**
     * 获取desc值
     * @param column
     * @return
     */
    public static <E extends Enum<E>> String getDesc(Enum<E> column) {
        if(null == column) {
            throw new IllegalArgumentException("传入的column为空");
        }
        try {
            return column.getClass().getField(column.name()).getAnnotation(Comment.class).desc();
        } catch (Exception e) {

            throw new RuntimeException("运行异常："+e.getMessage());
        }
    }
}
