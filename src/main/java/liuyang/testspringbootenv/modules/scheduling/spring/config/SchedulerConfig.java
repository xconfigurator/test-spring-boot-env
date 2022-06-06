package liuyang.testspringbootenv.modules.scheduling.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author liuyang(wx)
 * @since 2022/3/30
 */
@ConditionalOnProperty(prefix = "enable", name="modules.scheduler.spring", havingValue = "true")// 2022/6/6 实测，仅写到这里并不顶用。必须要标注到Service上。
@Configuration
@EnableScheduling
public class SchedulerConfig {

    /**
     * 定制线程池
     * 1. 如果不定制，也能跑。不过任务的执行线程会打印类似[pool-1-thread-1]，倒是能用，但这是哪个池子。
     * 2. Bean的名字，可以额参考@EnableScheduling的注释，就知道是怎么回事了。
     * 3. 对比
     * 异步线程池：
     * ThreadPoolTaskExecutor
     * 调度任务线程池：
     * ThreadPoolTaskScheduler
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        final int poolSize = 20;
        final int awaitTerminationSeconds = 60;
        ThreadPoolTaskScheduler  executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(poolSize);
        //executor.setThreadNamePrefix("schedulerTask-");
        //任务完成再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置任务等待时间,如果超过该值还没有销毁就强制销毁,确保最后关闭
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        return executor;
    }

}
