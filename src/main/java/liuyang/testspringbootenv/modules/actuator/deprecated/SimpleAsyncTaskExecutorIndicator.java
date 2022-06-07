package liuyang.testspringbootenv.modules.actuator.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang(wx)
 * @since 2022/4/18
 */
@Deprecated
//@Component // 这个不行 容器中没有simpleAsyncTaskSexecutor
public class SimpleAsyncTaskExecutorIndicator implements HealthIndicator {

    @Autowired
    private SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;

    @Override
    public Health health() {
        int concurrencyLimit = simpleAsyncTaskExecutor.getConcurrencyLimit();
        String parentThreadGroupName = simpleAsyncTaskExecutor.getThreadGroup().getParent().getName();
        String threadGroupName = simpleAsyncTaskExecutor.getThreadGroup().getName();
        String threadNamePrefix = simpleAsyncTaskExecutor.getThreadNamePrefix();
        int threadPriority = simpleAsyncTaskExecutor.getThreadPriority();

        Map<String, Object> info = new HashMap<>();
        info.put("currentcyLimit", concurrencyLimit);
        info.put("parentThreadGroupName", parentThreadGroupName);
        info.put("threadGroupName", threadGroupName);
        info.put("threadNamePrefix", threadNamePrefix);
        info.put("threadNamePrority", threadPriority);

        return Health.up().withDetails(info).build();
    }
}
