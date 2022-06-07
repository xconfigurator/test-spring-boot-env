package liuyang.testspringbootenv.modules.actuator.indicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author liuyang(wx)
 * @since 2022/6/7
 */
@Component
public class ThreadPoolTaskSchedulerIndicator implements HealthIndicator {

    //@Autowired
    //@Qualifier("taskScheduler")// 这个是在modules/scheduling/spring/config/SchedulerConfig.java中定义的。
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    @Qualifier("taskScheduler")// 这个是在modules/scheduling/spring/config/SchedulerConfig.java中定义的。
    public void setThreadPoolTaskScheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Override
    public Health health() {
        // 调度
        int poolSize = threadPoolTaskScheduler.getPoolSize();
        int activeCount = threadPoolTaskScheduler.getActiveCount();
        ThreadGroup threadGroup = threadPoolTaskScheduler.getThreadGroup();
        String threadGroupName = threadPoolTaskScheduler.getThreadGroup() == null ? "无ThreadGroup" : threadPoolTaskScheduler.getThreadGroup().getName();

        // JDK底层干活的
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = threadPoolTaskScheduler.getScheduledThreadPoolExecutor();

        Map<String, Object> info = new HashMap<>();
        info.put("poolSize", poolSize);
        info.put("activeCount", activeCount);
        info.put("threadGroupName", threadGroupName);

        // 判定down(略)

        return Health.up().withDetails(info).build();
    }
}
