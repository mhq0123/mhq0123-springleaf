package com.mhq0123.springleaf.message.fixedlength;

import com.alibaba.fastjson.JSONObject;
import com.mhq0123.springleaf.core.utils.SpringUtils;
import com.mhq0123.springleaf.message.common.annotation.FieldConvertEnhance;
import com.mhq0123.springleaf.message.common.interceptor.FieldConvertInterceptorInterface;
import com.mhq0123.springleaf.message.common.type.FieldConvertInterceptorAction;
import com.mhq0123.springleaf.message.common.type.FieldConvertInterceptorTime;
import com.mhq0123.springleaf.message.common.type.FieldConvertStrategyObtainType;
import com.mhq0123.springleaf.message.fixedlength.analysis.FieldFixedLengthAnalysis;
import com.mhq0123.springleaf.message.fixedlength.annotation.FieldFixedLength;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName MessageFixedLengthConverter
 * @date 2016-07-06
 * @memo 定长报文转换
 */
public abstract class MessageFixedLengthConverter implements Serializable {
    private static final long serialVersionUID = -3683618401509759213L;

    private static Logger logger = LoggerFactory.getLogger(MessageFixedLengthConverter.class);

    /**
     * 获取转换规则map  防止单例，都让子类实例化一个map
     * @return
     */
    public abstract Map<Field, FieldFixedLengthAnalysis> getFieldAnalysisMap();

    /**
     * 获取转换来源方式 默认是从数据库获取
     * @return
     */
    public FieldConvertStrategyObtainType getConvertStrategyObtainType(){
        return FieldConvertStrategyObtainType.DB;
    }

    /**
     * 获取转换规则 json（数据库一般存的是json格式的规则）->bean
     * @return
     */
    public abstract FieldFixedLengthAnalysis getFieldAnalysisByDB(String fieldCode);

    /**
     * 报文转换成bean
     * @param readLine
     */
    public void convertMessageToBean(String readLine) {
        Field[] fields = this.getClass().getDeclaredFields();
        if(null == fields) {
            return;
        }
        for(Field field : fields) {
            field.setAccessible(true);
            this.convertMessageToField(field, readLine);
        }
    }

    /**
     * bean 转换成报文
     * @return
     */
    public String convertBeanToMessage() {
        // TODO 待实现
        return null;
    }

    /**
     * 获取单个字段的转换策略bean
     * @param field
     * @return
     */
    private FieldFixedLengthAnalysis getFieldAnalysis(Field field) {
        if(null == field) {
            throw new IllegalArgumentException("字段为空");
        }

        if(!this.getFieldAnalysisMap().containsKey(field)) {
            FieldFixedLengthAnalysis fieldAnalysis = null;

            // 设置可访问
            field.setAccessible(true);

            FieldFixedLength fieldAnnotation = field.getAnnotation(FieldFixedLength.class);

            if(null != fieldAnnotation) {
                // 若是通过注解获取转换规则
                if(FieldConvertStrategyObtainType.ANNOTATION == this.getConvertStrategyObtainType()) {
                    Annotation[] annotationArray = field.getAnnotations();

                    if(null != annotationArray && annotationArray.length > 0) {
                        fieldAnalysis = new FieldFixedLengthAnalysis();
                        for(Annotation annotation : annotationArray) {
                            // 处理messageField注解
                            if(annotation instanceof FieldFixedLength) {
                                FieldFixedLength messageField = (FieldFixedLength)annotation;

                                fieldAnalysis.setName(messageField.name());
                                fieldAnalysis.setFieldClass(messageField.fieldClass());
                                fieldAnalysis.setStartPos(messageField.startPos());
                                fieldAnalysis.setLength(messageField.length());
                                fieldAnalysis.setPadType(messageField.padType());
                                fieldAnalysis.setMaterial(messageField.material());
                                fieldAnalysis.setRegex(messageField.regex());
                            }
                            // 处理FieldConvertEnhance增强注解
                            if(annotation instanceof FieldConvertEnhance) {
                                FieldConvertEnhance fieldConvertEnhance = (FieldConvertEnhance)annotation;
                                Class<? extends FieldConvertInterceptorInterface> interceptor = fieldConvertEnhance.interceptor();
                                // 判断
                                if(FieldConvertInterceptorTime.BEFORE == fieldConvertEnhance.time() && FieldConvertInterceptorAction.SET == fieldConvertEnhance.action()) {
                                    fieldAnalysis.addBeforeSetInterceptor(interceptor);
                                } else if(FieldConvertInterceptorTime.BEFORE == fieldConvertEnhance.time() && FieldConvertInterceptorAction.GET == fieldConvertEnhance.action()) {
                                    fieldAnalysis.addBeforeGetInterceptor(interceptor);
                                } else if(FieldConvertInterceptorTime.AFTER == fieldConvertEnhance.time() && FieldConvertInterceptorAction.SET == fieldConvertEnhance.action()) {
                                    fieldAnalysis.addAfterSetInterceptor(interceptor);
                                } else if(FieldConvertInterceptorTime.AFTER == fieldConvertEnhance.time() && FieldConvertInterceptorAction.GET == fieldConvertEnhance.action()) {
                                    fieldAnalysis.addAfterGetInterceptor(interceptor);
                                }
                            }
                        }
                    }
                } else if(FieldConvertStrategyObtainType.DB == this.getConvertStrategyObtainType()) {
                    // 若是转换策略来源数据库
                    fieldAnalysis = this.getFieldAnalysisByDB(fieldAnnotation.fieldCode());
                } else {
                    throw new UnsupportedOperationException("暂不支持的规则解析来源|" + this.getConvertStrategyObtainType());
                }
            }

            logger.info("解析后产生翻译分解bean:{}", JSONObject.toJSONString(fieldAnalysis, true));
            this.getFieldAnalysisMap().put(field, fieldAnalysis);
        }
        return this.getFieldAnalysisMap().get(field);
    }

    /**
     * 单个字段的报文转bean属性
     * @param field
     * @param readLine
     */
    private void convertMessageToField(Field field, String readLine) {
        if(null == field) {
            throw new IllegalArgumentException("字段为空");
        }
        if(StringUtils.isEmpty(readLine)) {
            throw new IllegalArgumentException("读取行为空");
        }
        FieldFixedLengthAnalysis fieldAnalysis = this.getFieldAnalysis(field);
        if(null == fieldAnalysis) {
            return;
        }
        int endPos = fieldAnalysis.getStartPos() + fieldAnalysis.getLength();
        // 获取字符串
        if(readLine.length() < endPos) {
            throw new IllegalArgumentException("行长度小于栏位所需长度|" + fieldAnalysis.getName() + "|" + readLine);
        }
        String fieldStr = readLine.substring(fieldAnalysis.getStartPos(), endPos);
        // 处理填充物
        fieldStr = fieldStr.replaceAll(String.valueOf(fieldAnalysis.getMaterial()), "");
        // 处理校验
        String regex = fieldAnalysis.getRegex();
        if(StringUtils.isNotEmpty(regex) && !fieldStr.matches(regex)) {
            throw new IllegalArgumentException(fieldAnalysis.getName() + "|格式校验不匹配|" + regex);
        }
        // 处理数据类型
        Class<?> fieldClass = fieldAnalysis.getFieldClass();
        Object value = ConvertUtils.convert(fieldStr, fieldClass);

        // 赋值前增强处理
        if(null != fieldAnalysis.getBeforeSetInterceptors()) {
            for(Class<? extends FieldConvertInterceptorInterface> interceptor : fieldAnalysis.getBeforeSetInterceptors()) {
                value = SpringUtils.getBean(interceptor).deal(value);
            }
        }
        try {
            field.set(this, value);
        } catch (Exception e) {
            logger.error(fieldAnalysis.getName() + "|" + readLine + "|赋值异常", e);
            throw new RuntimeException(fieldAnalysis.getName() + "|" + readLine + "|赋值异常|" + e.getMessage());
        }
        // 赋值后增强处理
        if(null != fieldAnalysis.getAfterSetInterceptors()) {
            for(Class<? extends FieldConvertInterceptorInterface> interceptor : fieldAnalysis.getAfterSetInterceptors()) {
                SpringUtils.getBean(interceptor).deal(value);
            }
        }
    }

    /**
     * bean字段生产报文
     * @param field
     * @return
     */
    private String convertBeanToMessage(Field field) {
        // TODO 待实现
        return null;
    }
}
