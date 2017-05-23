package org.springleaf.task.delay;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;

/**
 * @author mahq.
 * @projectName: wanrong.
 * @date: 2017/5/10 0010 11:32
 * @desc: 延迟任务处理工具类
 */
public class DelayedTaskComponent {

    private static Logger logger = LoggerFactory.getLogger(DelayedTaskComponent.class);

    /** 锁超时时间*/
    private final static long LOCK_OVERTIME_MILLISECOND = 60 * 1000L;
    /** 任务前缀*/
    private static String REDIS_TASK_PREFIX = "TASK_";
    /** 锁前缀*/
    private static String REDIS_LOCK_PREFIX = "LOCK_";
    /** 是否运行*/
    private static boolean isRun = true;

    /** 开启的任务*/
    private DelayedTaskEnum[] openTaskEnums;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    @Qualifier("kafkaTopicChannel")
    private MessageChannel messageChannel;

    /**
     * 开始获取任务线程
     */
    @PostConstruct
    private void startGetTaskThreads() {
        if(null == openTaskEnums || openTaskEnums.length == 0) {
            logger.info(">>>>>>>>>>>>>>>无延迟任务开启");
        }
        try {
            // 循环任务列表
            for(DelayedTaskEnum taskEnum : openTaskEnums) {
                logger.info(">>>>>>>>>>>>>>>延迟任务:{}线程启动---start", taskEnum);
                // 获取任务配置
                DelayedTaskAnnotation taskAnnotation = taskEnum.getDeclaringClass().getField(taskEnum.name()).getAnnotation(DelayedTaskAnnotation.class);
                logger.info(">>>>>>>>>>>>>>>延迟任务:{}线程配置:[topic:{},threadCount:{},sleepMin:{},sleepMax:{},sleepStep:{}]", taskEnum, taskAnnotation.topic(), taskAnnotation.threadCount(), taskAnnotation.sleepMin(), taskAnnotation.sleepMax(), taskAnnotation.sleepStep());
                // 创建线程
                for(int i=0; i< taskAnnotation.threadCount(); i++) {
                    new TaskGetThread(taskEnum).start();
                }
                logger.info(">>>>>>>>>>>>>>>延迟任务:{}启动---end", taskEnum);
            }
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>延迟任务启动异常", e);
        }
    }

    /**
     * 停止获取任务线程
     */
    @PreDestroy
    private void stopGetTaskThreads() {
        logger.info(">>>>>>>>>>>>>>>延迟任务关闭---start");
        isRun = false;
    }

    /**
     * 写入任务  即设置 分数 然后加入zset
     * @param delayedTask
     * @param taskValue
     * @param delayedMillisecond
     */
    public Long addTask(DelayedTaskEnum delayedTask, String taskValue, Long delayedMillisecond) {

        logger.info(">>>>>>>>>>>>>>param[delayedTask:{}, taskValue:{}, delayedMillisecond:{}]", delayedTask, taskValue, delayedMillisecond);

        // 分数即当前时间加上延迟后的时间
        int score = (int)((System.currentTimeMillis() + delayedMillisecond) / 1000);

        logger.info(">>>>>>>>>>>>>>score:{}", score);

        return jedisCluster.zadd(REDIS_TASK_PREFIX + delayedTask.name(), score, taskValue);
    }

    /**
     * 获取任务
     * @param delayedTask
     * @param taskAnnotation
     * @return
     */
    private String getTask(DelayedTaskEnum delayedTask, DelayedTaskAnnotation taskAnnotation) {
        if(null == delayedTask) {
            return null;
        }
        synchronized (delayedTask) {
            // 加锁
            if (!lock(delayedTask)) {
                return null;
            }
            String taskValue = null;
            try {
                int score = (int) (System.currentTimeMillis() / 1000);
                // 按时间分数正序取第一个
                Set<String> taskSet = jedisCluster.zrangeByScore(REDIS_TASK_PREFIX + delayedTask.name(), 0, score, 0, 1);
                if (null != taskSet && !taskSet.isEmpty()) {
                    // 原子内获取
                    taskValue = (String) taskSet.toArray()[0];
                    logger.info(">>>>>>>>>>>>>>[delayedTask:{}获取到任务:{}]", delayedTask, taskValue);
                    // 如果不为空则发送kafka
                    if(StringUtils.isNotEmpty(taskValue)) {
                        // 发送kafka
                        boolean isSuccess = send(taskValue, taskAnnotation);
                        if (!isSuccess) {
                            throw new RuntimeException("发送kafka失败");
                        }
                        logger.info(">>>>>>>>>>>>>>[delayedTask:{}发送kafka成功]", delayedTask);
                    }
                    // 发送成功后，原子内移除元素
                    Long count = jedisCluster.zrem(REDIS_TASK_PREFIX + delayedTask.name(), taskValue);
                    logger.info(">>>>>>>>>>>>>>[delayedTask:{}获取后移除count:{}]", delayedTask, count);
                } else {
                    logger.info(">>>>>>>>>>>>>>[delayedTask:{}暂无任务]", delayedTask);
                }
            } catch (Exception e) {
                logger.error(">>>>>>>>>>>>>获取任务异常", e);
            } finally {
                // 去锁
                unlock(delayedTask);
            }
            return taskValue;
        }
    }

    /**
     * 发送kafka
     * @param taskValue
     * @param taskAnnotation
     * @return
     */
    private boolean send(String taskValue, DelayedTaskAnnotation taskAnnotation) {
        int hashCode = taskValue.hashCode();
        String keyValue = String.valueOf(hashCode);
        // 计算分区
        int partitionId = Math.abs(hashCode % taskAnnotation.partitions());
        return messageChannel.send(MessageBuilder.withPayload(taskValue).setHeader(KafkaHeaders.TOPIC,
                                                                                   taskAnnotation.topic()).setHeader(KafkaHeaders.PARTITION_ID,
                                                                                                                                  partitionId).setHeader(KafkaHeaders.MESSAGE_KEY,
                                                                                                                                                         keyValue).build());
    }

    /**
     * 分布式redis 加锁
     * @param delayedTask
     * @return
     */
    private boolean lock(DelayedTaskEnum delayedTask) {
        if(null == delayedTask) {
            return false;
        }
        // 新锁的超时时间
        String newLockOverTime = String.valueOf(System.currentTimeMillis() + LOCK_OVERTIME_MILLISECOND + 1);
        // 加锁成功，直接返回
        long count = jedisCluster.setnx(REDIS_LOCK_PREFIX + delayedTask.name(), newLockOverTime);
        if(count == 1) {
//            logger.info(">>>>>>>>>>>>>>delayedTask:{}直接加锁成功", delayedTask);
            return true;
        }
        // 加锁失败，判断锁是否已超时
        String currentLockOverTime = jedisCluster.get(REDIS_LOCK_PREFIX + delayedTask.name());
        logger.info(">>>>>>>>>>>>>>delayedTask:{}当前锁超时时间:{}", delayedTask, currentLockOverTime);
        // 判断锁是否已超时
        if(StringUtils.isNotEmpty(currentLockOverTime) && Long.parseLong(currentLockOverTime) < System.currentTimeMillis()) {
            // 防止多个进程都进入到这里 所以判断下设置前的旧值
            String oldLockOverTime = jedisCluster.getSet(REDIS_LOCK_PREFIX + delayedTask.name(), newLockOverTime);
            logger.info(">>>>>>>>>>>>>>delayedTask:{}锁旧的超时时间:{}", delayedTask, oldLockOverTime);
            if(StringUtils.isNotEmpty(oldLockOverTime) && oldLockOverTime.equals(currentLockOverTime)) {
                // 设置前的也是自己获取的值则说明加锁成功
                logger.info(">>>>>>>>>>>>>>delayedTask:{}超时加锁成功", delayedTask);
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param delayedTask
     * @return
     */
    private boolean unlock(DelayedTaskEnum delayedTask) {
        long count = jedisCluster.del(REDIS_LOCK_PREFIX + delayedTask.name());
//        logger.info(">>>>>>>>>>>>>>delayedTask:{}解锁条数:{}", delayedTask, count);
        if(1 == count) {
            return true;
        }
        return false;
    }

    public void setOpenTaskEnums(DelayedTaskEnum[] openTaskEnums) {
        this.openTaskEnums = openTaskEnums;
    }

    /**
     * 获取任务的线程
     * 引入了一个弹性休眠机制
     */
    private class TaskGetThread extends Thread {

        private DelayedTaskEnum taskEnum;

        public TaskGetThread(DelayedTaskEnum taskEnum) {
            this.taskEnum = taskEnum;
        }

        @Override
        public void run() {
            logger.info(">>>>>>>>>>>>>>>延迟任务:{},线程:{},---启动", taskEnum, Thread.currentThread().getId());
            try {
                // 获取配置
                DelayedTaskAnnotation taskAnnotation = taskEnum.getDeclaringClass().getField(taskEnum.name()).getAnnotation(DelayedTaskAnnotation.class);
                // 初始睡眠为最大值
                long sleep = taskAnnotation.sleepMax();
                while(isRun) {
                    try {
                        // 获取任务
                        String taskValue = getTask(taskEnum, taskAnnotation);
                        logger.info(">>>>>>>>>>>>>>>延迟任务:{},线程:{},获取任务:{}", taskEnum, Thread.currentThread().getId(), taskValue);
                        // 如果为空 间隔延长,反之缩减
                        if(StringUtils.isEmpty(taskValue)) {
                            // 若是已经最大值了，则无需计算
                            if(sleep >= taskAnnotation.sleepMax()) {
                                sleep = taskAnnotation.sleepMax();
                            } else {
                                sleep = sleep + taskAnnotation.sleepStep() > taskAnnotation.sleepMax() ? taskAnnotation.sleepMax() : sleep + taskAnnotation.sleepStep();
                            }
                        } else {
                            // 若是已经最小值了，则无需计算
                            if(sleep <= taskAnnotation.sleepMin()) {
                                sleep = taskAnnotation.sleepMin();
                            } else {
                                sleep = sleep - taskAnnotation.sleepStep() < taskAnnotation.sleepMin() ? taskAnnotation.sleepMin() : sleep - taskAnnotation.sleepStep();
                            }
                        }
                        logger.info(">>>>>>>>>>>>>>>延迟任务:{},线程:{},休眠:{}", taskEnum, Thread.currentThread().getId(), sleep);
                        // 如果还要跑则休眠间隔
                        if(isRun) {
                            Thread.sleep(sleep);
                        }
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>延迟任务:{},线程:{},获取异常", taskEnum, Thread.currentThread().getId(), e);
                    }
                }
                logger.info(">>>>>>>>>>>>>>>延迟任务:{},线程:{},---关闭", taskEnum, Thread.currentThread().getId());
            } catch (Exception e) {
                logger.error(">>>>>>>>>>>>>>>延迟任务:{},线程:{},执行异常", taskEnum, Thread.currentThread().getId(), e);
            }
        }
    }
}
