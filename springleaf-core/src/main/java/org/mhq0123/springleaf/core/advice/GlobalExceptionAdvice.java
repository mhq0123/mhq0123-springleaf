package org.mhq0123.springleaf.core.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * project: springleaf
 * author:  mhq0123
 * date:    2017/1/8.
 * desc:    统一异常处理
 */
public class GlobalExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest httpServletRequest, Exception e)  {

        //打印异常信息：
        logger.error(">>>>>>>>>>>>>>exception:" + e.getMessage(), e);

       /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：@ResponseBody
        * 添加return即可。
        */

       /*
        * 返回视图：
        * 定义一个ModelAndView即可，
        * mav.addObject("exception", e);
        * mav.addObject("url", req.getRequestURL());
        * mav.setViewName(DEFAULT_ERROR_VIEW);
        * 然后return;
        * 定义视图文件(比如：error.html,error.ftl,error.jsp);
        *
        */
    }

}
