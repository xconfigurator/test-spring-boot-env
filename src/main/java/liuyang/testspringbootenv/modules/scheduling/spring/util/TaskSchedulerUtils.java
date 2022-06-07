package liuyang.testspringbootenv.modules.scheduling.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 动态添加删除任务
 * 1. 对Spring的ThreadPoolTaskScheduler进行薄层封装。
 * 2. 如果需要更丰富的性能，可以考虑更换使用QuartzUtils。
 *
 * @author liuyang(wx)
 * @since 2022/6/6
 */
@ConditionalOnBean(ThreadPoolTaskScheduler.class)// 如果配有配置ThreadPoolTaskScheduler的实例，则容器不会初始化TaskSchedulerUtils实例。
@Component
public class TaskSchedulerUtils {
    private static Logger log = LoggerFactory.getLogger(TaskSchedulerUtils.class);

    private ConcurrentHashMap<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();

    // 使用TaskSchedulerUtils必须在SchedulerConfig中配置ThreadPolTaskScheduler实例。
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * CRON表达式
     * @param taskId    任务ID
     * @param cron      CRON表达式
     * @param task  任务
     */
    public void addPeriodic(String taskId, String cron, Runnable task) {
        log.info("taskId = {}, cron表达式 = {}", taskId, cron);
        stop(taskId);
        scheduledFutureMap.put(taskId, threadPoolTaskScheduler.schedule(task, new CronTrigger(cron)));
    }

    /**
     * 固定时间间隔
     * @param taskId    任务ID
     * @param delay     间隔时间
     * @param task  任务
     */
    public void addPeriodic(String taskId, long delay, Runnable task) {
        log.info("taskId = {}, delay = {}", taskId, delay);
        stop(taskId);
        scheduledFutureMap.put(taskId, threadPoolTaskScheduler.scheduleWithFixedDelay(task, delay));
    }

    /**
     * 停止所有任务
     *
     */
    public void stopAll() {
        for (String taskId : scheduledFutureMap.keySet()) {
            stop(taskId);
        }
    }

    /**
     * 停止任务
     *
     * @param taskId
     */
    public void stop(String taskId) {
        if (null == taskId) return;
        stop(scheduledFutureMap.get(taskId));
        scheduledFutureMap.remove(taskId);
    }

    /**
     * 停止任务
     * @param scheduledFuture
     */
    private void stop(ScheduledFuture scheduledFuture) {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    /**
     * 为了方便监控
     * @return
     */
    public ConcurrentHashMap<String, ScheduledFuture> getScheduledFutureMap() {
        return this.scheduledFutureMap;
    }
}
