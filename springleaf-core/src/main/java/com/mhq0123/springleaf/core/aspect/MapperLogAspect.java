package com.mhq0123.springleaf.core.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    mapper层日志切面
 */
@Aspect
@Component
public class MapperLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(MapperLogAspect.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意开头
     * ~ 第三个 * 代表任意方法.
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.mhq0123..*Mapper.*(..))")
    public void mapperLog(){}

    @Before("mapperLog()")
    public void doBefore(ProceedingJoinPoint proceedingJoinPoint){
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        logger.info(">>>>>>>>>>>>>>MapperLogAspect.doBefore");
        logger.info(">>>>>>>>>>>>>>class:{},method:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        logger.info(">>>>>>>>>>>>>>args:{}", JSONObject.toJSONString(proceedingJoinPoint.getArgs(), true));
    }

    @AfterReturning("mapperLog()")
    public void  doAfterReturning(ProceedingJoinPoint proceedingJoinPoint){
        try {
            // 处理完请求，返回内容
            logger.info(">>>>>>>>>>>>>>MapperLogAspect.doAfterReturning");
            logger.info(">>>>>>>>>>>>>>return:{}", proceedingJoinPoint.proceed());
        } catch (Throwable t) {
            logger.error(">>>>>>>>>>>>>>execute exception:{}", t.getMessage(), t);
        }
        logger.info(">>>>>>>>>>>>>>cost time:{}", System.currentTimeMillis() - startTime.get());
    }
}
