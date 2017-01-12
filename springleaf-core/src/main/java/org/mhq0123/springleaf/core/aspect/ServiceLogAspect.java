package org.mhq0123.springleaf.core.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    service层日志切面
 */
@Aspect
@Component
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @PostConstruct
    public void init() {
        logger.info(">>>>>>>>>>>>>>ServiceLogAspect started ...");
    }

    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意开头
     * ~ 第三个 * 代表任意方法.
     * ~ .. 匹配任意数量的参数.
     */
    @Around("execution(* com.mhq0123..*Service.*(..))")
    public Object Around(ProceedingJoinPoint proceedingJoinPoint){
        Object object = null;
        try {
            // 接收到请求，记录请求内容
            logger.info(">>>>>>>>>>>>>>service:{},method:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
            logger.info(">>>>>>>>>>>>>>args:{}", JSONObject.toJSONString(proceedingJoinPoint.getArgs(), true));
            // 处理完请求，返回内容
            Long startTime = System.currentTimeMillis();
            object = proceedingJoinPoint.proceed();
            logger.info(">>>>>>>>>>>>>>cost time:{}", System.currentTimeMillis() - startTime);
            logger.info(">>>>>>>>>>>>>>return:{}", JSONObject.toJSONString(object, true));
            // 返回
            return object;
        } catch (Throwable t) {
            logger.error(">>>>>>>>>>>>>>execute exception:{}", t.getMessage(), t);
            throw new RuntimeException(t);
        }
    }
}
