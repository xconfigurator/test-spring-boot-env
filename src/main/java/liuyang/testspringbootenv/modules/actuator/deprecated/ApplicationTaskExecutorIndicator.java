package liuyang.testspringbootenv.modules.actuator.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang(wx)
 * @since 2022/4/18
 */
@Deprecated
// @Component
public class ApplicationTaskExecutorIndicator implements HealthIndicator {

    @Autowired
    @Qualifier("applicationTaskExecutor") // 202204181502 实测，加上定制的线程池之后容器就不存在applicationTaskExecutor了。
    private ThreadPoolTaskExecutor executor;

    @Override
    public Health health() {
        // 核心线程数
        int corePoolSize = executor.getCorePoolSize();
        // 最大线程数
        int maxPoolSize = executor.getMaxPoolSize();
        // 线程池维护线程允许的空闲时间
        int keepAliveSeconds = executor.getKeepAliveSeconds();
        // queueCapacity 貌似executor拿不到
        // 活跃线程数
        int activeCount = executor.getActiveCount();
        // 当前线程池中线程数
        int poolSize = executor.getPoolSize();



        Map<String, Object> info = new HashMap<>();
        info.put("核心线程数", corePoolSize);
        info.put("最大线程数", maxPoolSize);
        info.put("线程池维护线程允许的空闲时间", keepAliveSeconds);
        info.put("活跃线程数", activeCount);
        info.put("当前线程池中的线程数", poolSize);

        /*
        // 判定down
        double rate = BigDecimal.valueOf(activeCount)
                .divide(BigDecimal.valueOf(maxPoolSize))
                .doubleValue();
        if (rate > 0.8) {
            return Health.down().withDetails(info).build();
        } else {
            return Health.up().withDetails(info).build();
        }*/
        return Health.up().withDetails(info).build();
    }
}
