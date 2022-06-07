package liuyang.testspringbootenv.modules.actuator.indicators;

import liuyang.testspringbootenv.modules.scheduling.spring.util.TaskSchedulerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author liuyang(wx)
 * @since 2022/6/7
 */
@ConditionalOnBean(TaskSchedulerUtils.class)
@Component
public class TaskSchedulerUtilsIndicator implements HealthIndicator {

    //@Autowired
    private TaskSchedulerUtils taskSchedulerUtils;

    @Autowired
    public void setTaskSchedulerUtils(TaskSchedulerUtils taskSchedulerUtils) {
        this.taskSchedulerUtils = taskSchedulerUtils;
    }

    @Override
    public Health health() {
        ConcurrentHashMap<String, ScheduledFuture> scheduledFutureMap = taskSchedulerUtils.getScheduledFutureMap();

        Map<String, Object> info = new HashMap<>();
        info.put("taskIds", scheduledFutureMap.keys());

        return Health.up().withDetails(info).build();
    }
}
