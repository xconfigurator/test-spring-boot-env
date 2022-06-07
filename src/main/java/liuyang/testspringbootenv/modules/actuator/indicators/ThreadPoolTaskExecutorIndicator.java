package liuyang.testspringbootenv.modules.actuator.indicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liuyang(wx)
 * @since 2022/4/18
 */
@Component
public class ThreadPoolTaskExecutorIndicator implements HealthIndicator {

    //@Autowired
    //@Qualifier("asyncExecutor")// 这个是在modules/async/config/AsyncConfig.java中定义
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Qualifier("asyncExecutor")// 这个是在modules/async/config/AsyncConfig.java中定义
    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public Health health() {
        // 核心线程数
        int corePoolSize = threadPoolTaskExecutor.getCorePoolSize();
        // 最大线程数
        int maxPoolSize = threadPoolTaskExecutor.getMaxPoolSize();
        // 线程池维护线程允许的空闲时间
        int keepAliveSeconds = threadPoolTaskExecutor.getKeepAliveSeconds();
        // queueCapacity 貌似executor拿不到
        // 活跃线程数
        int activeCount = threadPoolTaskExecutor.getActiveCount();
        // 当前线程池中线程数
        int poolSize = threadPoolTaskExecutor.getPoolSize();

        // JDK 底层干活的
        ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();

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
