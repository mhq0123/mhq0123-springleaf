package org.mhq0123.springleaf.core.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    controller层日志切面
 */
@Aspect
@Component
public class RestfulLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestfulLogAspect.class);

    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意开头
     * ~ 第三个 * 代表任意方法.
     * ~ .. 匹配任意数量的参数.
     */
    @Around("execution(* com.mhq0123..*Restful.*(..))")
    public JSONObject around(ProceedingJoinPoint proceedingJoinPoint){
        JSONObject jsonObject = new JSONObject();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 接收到请求，记录请求内容
            logger.info(">>>>>>>>>>>>>>path:{},httpMethod:{}", request.getRequestURI(), request.getMethod());
            logger.info(">>>>>>>>>>>>>>requestIp:{}", request.getRemoteAddr());
            logger.info(">>>>>>>>>>>>>>restful:{},method:{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
            logger.info(">>>>>>>>>>>>>>args:{}", JSONObject.toJSONString(proceedingJoinPoint.getArgs(), true));
            // 处理完请求，返回内容
            Long startTime = System.currentTimeMillis();
            Object object = proceedingJoinPoint.proceed();
            logger.info(">>>>>>>>>>>>>>cost time:{}", System.currentTimeMillis() - startTime);
            logger.info(">>>>>>>>>>>>>>return:{}", JSONObject.toJSONString(object, true));
            // 同意处理返回json
            jsonObject.put("status", "SUCCESS");
            jsonObject.put("result", object);
        } catch (Throwable t) {
            logger.error(">>>>>>>>>>>>>>execute exception:{}", t.getMessage(), t);
            jsonObject.put("status", "FAILURE");
            jsonObject.put("message", t.getMessage());
        }
        return jsonObject;
    }
}
