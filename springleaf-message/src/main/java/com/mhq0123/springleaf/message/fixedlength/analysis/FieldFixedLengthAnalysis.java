package com.mhq0123.springleaf.message.fixedlength.analysis;

import com.mhq0123.springleaf.message.common.interceptor.FieldConvertInterceptorInterface;
import com.mhq0123.springleaf.message.common.type.FieldConvertPadType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldFixedLengthAnalysis
 * @date 2016-07-06
 * @memo 定长字段解析分解bean
 */
public class FieldFixedLengthAnalysis {
    private static final long serialVersionUID = 5344263491780567760L;

    /** 属性名称 */
    private String name;
    /** 该字段的起始位置*/
    private int startPos;
    /** 该字段的长度*/
    private int length;
    /** 该字段的填充类型*/
    private FieldConvertPadType padType;
    /** 该字段的填充物*/
    private char material;
    /** 字段类型*/
    private Class<?> fieldClass;
    /** 匹配正则表达式*/
    private String regex;

    /** 获值之前*/
    private List<Class<? extends FieldConvertInterceptorInterface>> beforeGetInterceptors;
    /** 获值之后*/
    private List<Class<? extends FieldConvertInterceptorInterface>> afterGetInterceptors;
    /** 赋值之前*/
    private List<Class<? extends FieldConvertInterceptorInterface>> beforeSetInterceptors;
    /** 赋值之后*/
    private List<Class<? extends FieldConvertInterceptorInterface>> afterSetInterceptors;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStartPos() {
        return startPos;
    }
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public FieldConvertPadType getPadType() {
        return padType;
    }
    public void setPadType(FieldConvertPadType padType) {
        this.padType = padType;
    }
    public char getMaterial() {
        return material;
    }
    public void setMaterial(char material) {
        this.material = material;
    }
    public Class<?> getFieldClass() {
        return fieldClass;
    }
    public void setFieldClass(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }
    public List<Class<? extends FieldConvertInterceptorInterface>> getBeforeGetInterceptors() {
        return beforeGetInterceptors;
    }
    public void addBeforeGetInterceptor(Class<? extends FieldConvertInterceptorInterface> interceptor) {
        if (beforeGetInterceptors == null) {
            beforeGetInterceptors = new ArrayList<Class<? extends FieldConvertInterceptorInterface>>();
        }
        beforeGetInterceptors.add(interceptor);
    }
    public List<Class<? extends FieldConvertInterceptorInterface>> getAfterGetInterceptors() {
        return afterGetInterceptors;
    }
    public void addAfterGetInterceptor(Class<? extends FieldConvertInterceptorInterface> interceptor) {
        if (afterGetInterceptors == null) {
            afterGetInterceptors = new ArrayList<Class<? extends FieldConvertInterceptorInterface>>();
        }
        afterGetInterceptors.add(interceptor);
    }
    public List<Class<? extends FieldConvertInterceptorInterface>> getBeforeSetInterceptors() {
        return beforeSetInterceptors;
    }
    public void addBeforeSetInterceptor(Class<? extends FieldConvertInterceptorInterface> interceptor) {
        if (beforeSetInterceptors == null) {
            beforeSetInterceptors = new ArrayList<Class<? extends FieldConvertInterceptorInterface>>();
        }
        beforeSetInterceptors.add(interceptor);
    }
    public List<Class<? extends FieldConvertInterceptorInterface>> getAfterSetInterceptors() {
        return afterSetInterceptors;
    }
    public void addAfterSetInterceptor(Class<? extends FieldConvertInterceptorInterface> interceptor) {
        if (afterSetInterceptors == null) {
            afterSetInterceptors = new ArrayList<Class<? extends FieldConvertInterceptorInterface>>();
        }
        afterSetInterceptors.add(interceptor);
    }
    public String getRegex() {
        return regex;
    }
    public void setRegex(String regex) {
        this.regex = regex;
    }
    public void setBeforeGetInterceptors(List<Class<? extends FieldConvertInterceptorInterface>> beforeGetInterceptors) {
        this.beforeGetInterceptors = beforeGetInterceptors;
    }
    public void setAfterGetInterceptors(List<Class<? extends FieldConvertInterceptorInterface>> afterGetInterceptors) {
        this.afterGetInterceptors = afterGetInterceptors;
    }
    public void setBeforeSetInterceptors(List<Class<? extends FieldConvertInterceptorInterface>> beforeSetInterceptors) {
        this.beforeSetInterceptors = beforeSetInterceptors;
    }
    public void setAfterSetInterceptors(List<Class<? extends FieldConvertInterceptorInterface>> afterSetInterceptors) {
        this.afterSetInterceptors = afterSetInterceptors;
    }
}
