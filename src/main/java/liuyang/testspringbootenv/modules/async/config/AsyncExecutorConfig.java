package liuyang.testspringbootenv.modules.async.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liuyang(wx)
 * @since 2022/3/30 20220330 从启动类独立出来
 *
 * @update 20220418 如果不配置线程池，Spring Boot会默认配置，也好使。
 *                  Spring Boot 2.0.x会默认使用SimpleAsyncTaskExecutor这个非池化的实例，在应对周期性的并发短任务的时候可能会出现问题。
 *                  Spring Boot 2.1.x没有沿用，打印出线程名类似task-6，但是否池化未测试。
 *                  综上，可以明确指定使用的线程池。
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncExecutorConfig implements AsyncConfigurer {

    // 定制线程池
    @Bean
    public ThreadPoolTaskExecutor asyncExecutor() {
        // 明确定义异步任务使用线程池，@Async在不指定线程池名称时默认就是用这个线程池中的线程。
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12);// 12 核
        executor.setMaxPoolSize(7 * 20 + 20 + 10);// 每个接口同步任务7， 20个对端设备。 20个心跳检测线程。 10个实时任务
        executor.setKeepAliveSeconds(30);
        executor.setQueueCapacity(12);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //executor.setAllowCoreThreadTimeOut(true);
        //executor.setAwaitTerminationSeconds(30);
        return executor;
    }

    // 告诉框架，用我定制好的线程池。
    // 使用asyncExecutor这个名字的Bean是通过重写下面这个方法来实现的。
    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable throwable, Method method, Object... obj) -> {
            log.error(throwable.getLocalizedMessage(), throwable);
        };
    }
}
