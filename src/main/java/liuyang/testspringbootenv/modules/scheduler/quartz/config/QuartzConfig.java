package liuyang.testspringbootenv.modules.scheduler.quartz.config;

import liuyang.testspringbootenv.modules.scheduler.quartz.quartzjobbean.Hello01Job;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyang
 * @scine 2021/9/6
 */
@Configuration
public class QuartzConfig {
    private static final String JOB_GROUP_NAME = "LIUYANG_QUARTZ_SINGLE_JOB_GROUP_NAME";
    private static final String TRIGGER_GROUP_NAME = "LIUYANG_QUARTZ_SINGLE_TRIGGER_GROUP_NAME";

    @Bean // 疑问点：JobDetail和Trigger有没有必要使用Spring容器托管？ 答：这是文档推荐写法
    public JobDetail helloJobDetail01() {
        JobDetail jobDetail = JobBuilder.newJob(Hello01Job.class)
                .withIdentity("helloJobDetail01", JOB_GROUP_NAME)
                //.usingJobData("param1", "hello")
                //.usingJobData("param2", "quartz")
                .usingJobData("param1", "hello from JobDetail")
                .storeDurably() // storeDurably的意思是，当一个job没有trigger关联的时候是否应该被保存起来。Spring Boot这种写法
                // 经测试，如果不设置这个项，且采用scheduler.scheduleJob(jobDetail, trigger)方式触发，则在一次任务执行完毕后，该jobDetail会被quartz自动清理出scheduler的上下文。
                .build();
        return jobDetail;
    }

    @Bean // 疑问点：JobDetail和Trigger有没有必要使用Spring容器托管？ 答；这是文档推荐写法。
    public Trigger helloJobTrigger01(JobDetail helloJobDetail01) {
        // 每隔2秒一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/25 * * * * ?");

        // 创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(helloJobDetail01)// 关联JobDetail // 也可以通过SchedulerFactoryBean来建立联系，参见customize方法。（这个方法来自于SchedulerFactoryBeanCustomizer接口），貌似不可以！ 2021/5/31
                .withIdentity("helloJobTrigger01", TRIGGER_GROUP_NAME)
                .withSchedule(cronScheduleBuilder) // 触发策略(Simple, Cron, 日历)
                //.startNow()
                //.startAt()
                .usingJobData("param2", "quartz from Trigger")
                .build();

        return trigger;
    }
}
