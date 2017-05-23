package org.springleaf.task.delay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mahq.
 * @projectName: wanrong.
 * @date: 2017/5/18 0018 11:27
 * @desc:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
public @interface DelayedTaskAnnotation {

    /**
     * 对应的消息topic
     * @return
     */
    String topic();

    /**
     * 消息分区数
     * @return
     */
    int partitions() default 4;

    /**
     * 获取任务的线程数
     * @return
     */
    int threadCount() default 1;

    /**
     * 获取任务的间隔最大时间 毫秒
     * @return
     */
    long sleepMax() default 10 * 1000L;

    /**
     * 获取任务的间隔最小时间 毫秒
     * @return
     */
    long sleepMin() default 100L;

    /**
     * 间隔步长，当获取到任务间隔缩小，获取不到间隔延长
     * @return
     */
    long sleepStep() default 100L;
}
