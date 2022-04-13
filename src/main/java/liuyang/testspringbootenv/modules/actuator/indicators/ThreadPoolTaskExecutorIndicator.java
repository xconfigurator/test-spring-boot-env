package liuyang.testspringbootenv.modules.actuator.indicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步方法
 *
 * 参考文档：
 * 监控
 * https://blog.csdn.net/a20023930/article/details/110918477
 * 默认线程池问题
 * https://blog.csdn.net/z69183787/article/details/108610381
 *
 * @author liuyang(wx)
 * @since 2022/4/13
 */
@Component
public class ThreadPoolTaskExecutorIndicator implements HealthIndicator {

    @Autowired
    private ThreadPoolTaskExecutor executor;// 参考TaskExecutionAutoConfiguration.java

    @Override
    public Health health() {
        // 核心线程数
        int corePoolSize = executor.getCorePoolSize();
        // 活跃线程数
        int activeCount = executor.getActiveCount();
        // 当前线程池中线程数
        int poolSize = executor.getPoolSize();
        // 最大线程数
        int maxPoolSize = executor.getMaxPoolSize();

        // 判定down
        double rate = BigDecimal.valueOf(activeCount)
                .divide(BigDecimal.valueOf(maxPoolSize))
                .doubleValue();

        Map<String, Object> info = new HashMap<>();
        info.put("核心线程数", corePoolSize);
        info.put("活跃线程数", activeCount);
        info.put("当前线程池中的线程数", poolSize);
        info.put("最大线程数", maxPoolSize);

        if (rate > 0.8) {
            return Health.down().withDetails(info).build();
        } else {
            return Health.up().withDetails(info).build();
        }
    }
}
