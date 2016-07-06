package com.mhq0123.springleaf.message.separatorsplit.analysis;

import com.mhq0123.springleaf.message.common.interceptor.FieldConvertInterceptorInterface;
import com.mhq0123.springleaf.message.common.type.FieldConvertPadType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName FieldSeparatorSplitAnalysis
 * @date 2016-07-06
 * @memo 字符分隔字段解析分解bean
 */
public class FieldSeparatorSplitAnalysis implements Serializable {
    private static final long serialVersionUID = 5344263491780567760L;

    /** 属性名称 */
    private String name;
    /** 栏位代码*/
    public String fieldCode;
    /** 分隔符*/
    public String separator;
    /** 分割序号*/
    public int index;
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
    public String getSeparator() {
        return separator;
    }
    public void setSeparator(String separator) {
        this.separator = separator;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getFieldCode() {
        return fieldCode;
    }
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
    public void setBeforeGetInterceptors(
            List<Class<? extends FieldConvertInterceptorInterface>> beforeGetInterceptors) {
        this.beforeGetInterceptors = beforeGetInterceptors;
    }
    public void setAfterGetInterceptors(
            List<Class<? extends FieldConvertInterceptorInterface>> afterGetInterceptors) {
        this.afterGetInterceptors = afterGetInterceptors;
    }
    public void setBeforeSetInterceptors(
            List<Class<? extends FieldConvertInterceptorInterface>> beforeSetInterceptors) {
        this.beforeSetInterceptors = beforeSetInterceptors;
    }
    public void setAfterSetInterceptors(
            List<Class<? extends FieldConvertInterceptorInterface>> afterSetInterceptors) {
        this.afterSetInterceptors = afterSetInterceptors;
    }
}
