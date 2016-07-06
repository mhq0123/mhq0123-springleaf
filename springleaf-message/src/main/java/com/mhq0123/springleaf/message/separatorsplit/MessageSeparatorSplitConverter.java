package com.mhq0123.springleaf.message.separatorsplit;

import com.alibaba.fastjson.JSONObject;
import com.mhq0123.springleaf.core.utils.SpringUtils;
import com.mhq0123.springleaf.message.common.annotation.FieldConvertEnhance;
import com.mhq0123.springleaf.message.common.interceptor.FieldConvertInterceptorInterface;
import com.mhq0123.springleaf.message.common.type.FieldConvertInterceptorAction;
import com.mhq0123.springleaf.message.common.type.FieldConvertInterceptorTime;
import com.mhq0123.springleaf.message.common.type.FieldConvertStrategyObtainType;
import com.mhq0123.springleaf.message.separatorsplit.analysis.FieldSeparatorSplitAnalysis;
import com.mhq0123.springleaf.message.separatorsplit.annotation.FieldSeparatorSplit;
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
 * @fileName MessageSeparatorSplitConverter
 * @date 2016-07-06
 * @memo 分隔符文本报文转换
 */
public abstract class MessageSeparatorSplitConverter implements Serializable{
    private static final long serialVersionUID = -3683618401509759213L;

    private static Logger logger = LoggerFactory.getLogger(MessageSeparatorSplitConverter.class);

    /**
     * 获取解析map
     * @return
     */
    public abstract Map<Field, FieldSeparatorSplitAnalysis> getFieldAnalysisMap();

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
    public abstract FieldSeparatorSplitAnalysis getFieldAnalysisByDB(String fieldCode);

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
     * 获取配置bean
     * @param field
     * @return
     */
    private FieldSeparatorSplitAnalysis getFieldAnalysis(Field field) {
        if(null == field) {
            throw new IllegalArgumentException("字段为空");
        }

        if(!this.getFieldAnalysisMap().containsKey(field)) {
            FieldSeparatorSplitAnalysis fieldAnalysis = null;

            // 设置可访问
            field.setAccessible(true);

            FieldSeparatorSplit fieldAnnotation = field.getAnnotation(FieldSeparatorSplit.class);

            if(null != fieldAnnotation) {
                if(FieldConvertStrategyObtainType.ANNOTATION == this.getConvertStrategyObtainType()) {
                    Annotation[] annotationArray = field.getAnnotations();

                    if(null != annotationArray && annotationArray.length > 0) {
                        fieldAnalysis = new FieldSeparatorSplitAnalysis();
                        for(Annotation annotation : annotationArray) {
                            // 处理messageField注解
                            if(annotation instanceof FieldSeparatorSplit) {
                                FieldSeparatorSplit messageField = (FieldSeparatorSplit)annotation;

                                fieldAnalysis.setName(messageField.name());
                                fieldAnalysis.setSeparator(messageField.separator());
                                fieldAnalysis.setIndex(messageField.index());
                                fieldAnalysis.setFieldCode(messageField.fieldCode());
                                fieldAnalysis.setFieldClass(messageField.fieldClass());
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
                    fieldAnalysis = this.getFieldAnalysisByDB(fieldAnnotation.fieldCode());
                } else {
                    throw new UnsupportedOperationException("暂不支持的规则解析来源|" + this.getConvertStrategyObtainType());
                }
            }


            logger.info("解析后产生翻译bean:{}", JSONObject.toJSONString(fieldAnalysis, true));
            this.getFieldAnalysisMap().put(field, fieldAnalysis);
        }
        return this.getFieldAnalysisMap().get(field);
    }

    /**
     * 赋值
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
        FieldSeparatorSplitAnalysis fieldAnalysis = this.getFieldAnalysis(field);
        if(null == fieldAnalysis) {
            return;
        }
        String[] fieldStrArray = readLine.split(fieldAnalysis.getSeparator());
        if(fieldStrArray.length < fieldAnalysis.getIndex() + 1) {
            throw new IllegalArgumentException(fieldAnalysis.getName() + "|栏位索引超过数组长度" + "|" + readLine);
        }
        String fieldStr = fieldStrArray[fieldAnalysis.getIndex()];
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


}
