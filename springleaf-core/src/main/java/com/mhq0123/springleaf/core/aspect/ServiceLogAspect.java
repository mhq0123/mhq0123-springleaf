package com.mhq0123.springleaf.core.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    service层日志切面
 */
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

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
    @Pointcut("execution(public * com.mhq0123..*Service.*(..))")
    public void serviceLog(){}

    @Before("serviceLog()")
    public void doBefore(ProceedingJoinPoint proceedingJoinPoint){
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        logger.info(">>>>>>>>>>>>>>ServiceLogAspect.doBefore");
        logger.info(">>>>>>>>>>>>>>class:{},method:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
        logger.info(">>>>>>>>>>>>>>args:{}", JSONObject.toJSONString(proceedingJoinPoint.getArgs(), true));
    }

    @AfterReturning("serviceLog()")
    public void  doAfterReturning(ProceedingJoinPoint proceedingJoinPoint){
        try {
            // 处理完请求，返回内容
            logger.info(">>>>>>>>>>>>>>ServiceLogAspect.doAfterReturning");
            logger.info(">>>>>>>>>>>>>>return:{}", proceedingJoinPoint.proceed());
        } catch (Throwable t) {
            logger.error(">>>>>>>>>>>>>>ServiceLogAspect.doAfterReturning exception:{}", t.getMessage(), t);
        }
        logger.info(">>>>>>>>>>>>>>cost time:{}", System.currentTimeMillis() - startTime.get());

    }
}
