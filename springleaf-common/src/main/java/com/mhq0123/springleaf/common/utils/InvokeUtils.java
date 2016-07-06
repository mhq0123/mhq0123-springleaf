package com.mhq0123.springleaf.common.utils;

import com.mhq0123.springleaf.common.bean.InvokeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName InvokeUtils
 * @date 2016-07-06
 * @memo 异常工具类
 */
public class InvokeUtils {
    public static Logger logger = LoggerFactory.getLogger(InvokeUtils.class);

    /**
     * 查找当前方法的父级调用
     *
     * @return
     */
    public static InvokeInfo findInvokeInfo() {

        try {

            // 抛出一个试探性异常
            throw new Exception();

        } catch (Exception e) {

            StackTraceElement[] stackTraces = e.getStackTrace();

            if (stackTraces.length < 2) {
                logger.error("堆栈深度小于2");
                return null;
            }

            StackTraceElement parentStackTrace = stackTraces[2];

            // 获取调用信息
            String className = parentStackTrace.getClassName();
            String fileName = parentStackTrace.getFileName();
            int lineNumber = parentStackTrace.getLineNumber();
            String methodName = parentStackTrace.getMethodName();

            // 调用信息
            InvokeInfo invokeInfo = new InvokeInfo();{
                invokeInfo.setClassName(className);
                invokeInfo.setFileName(fileName);
                invokeInfo.setLineNumber(lineNumber);
                invokeInfo.setMethodName(methodName);
            }

            return invokeInfo;
        }

    }
}
