package liuyang.testspringbootenv.modules.actuator.indicators;

import liuyang.testspringbootenv.modules.scheduling.spring.config.SchedulerConfig;
import liuyang.testspringbootenv.modules.scheduling.spring.util.TaskSchedulerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author liuyang(wx)
 * @since 2022/6/7
 */
@ConditionalOnBean(TaskSchedulerUtils.class)
@Component// 20221122 发现关闭schduling.spring包下组件后，这里注入会出错
@Slf4j
public class TaskSchedulerUtilsIndicator implements HealthIndicator {

    //@Autowired
    private TaskSchedulerUtils taskSchedulerUtils;

    //@Autowired // 这里这个注解并不是必须的
    public void setTaskSchedulerUtils(TaskSchedulerUtils taskSchedulerUtils) {
        this.taskSchedulerUtils = taskSchedulerUtils;
    }

    @Override
    public Health health() {
        // 方法1 通过get方法拿到属性
        // 适用：该类提供了get方法
        // return type01();

        // 方法2 通过反射
        // 适用：该类没有提供get方法且该字段私有。
        return type02();
    }

    private Health type01() {
        log.info("通过get方法获得私有字段");
        // 获取监控字段
        ConcurrentHashMap<String, ScheduledFuture> scheduledFutureMap = taskSchedulerUtils.getScheduledFutureMap();
        // 抽取潜孔信息
        Map<String, Object> info = new HashMap<>();
        info.put("taskIds", scheduledFutureMap.keys());
        return Health.up().withDetails(info).build();
    }

    private Health type02() {
        log.info("通过反射方式获得私有字段");
        try {
            // 获得监控字段
            Field scheduledFutureMapField = null;
            scheduledFutureMapField = taskSchedulerUtils.getClass().getDeclaredField("scheduledFutureMap");
            scheduledFutureMapField.setAccessible(true);
            ConcurrentHashMap<String, ScheduledFuture> scheduledFutureMap = (ConcurrentHashMap<String, ScheduledFuture>) scheduledFutureMapField.get(taskSchedulerUtils);
            // 抽取监控信息
            Map<String, Object> info = new HashMap<>();
            info.put("taskIds", scheduledFutureMap.keys());
            return Health.up().withDetails(info).build();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Health.up().withDetail("反射出错了，测不到啊", "啊啊啊啊").build();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return Health.up().withDetail("反射出错了，测不到啊", "啊啊啊啊").build();
        }
    }
}
