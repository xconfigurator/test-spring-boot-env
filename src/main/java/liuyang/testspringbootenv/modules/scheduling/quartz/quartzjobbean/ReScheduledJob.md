#### 尝试改变运行调度周期的job
> TODO 只是一个示例，没有完工。

```java
@Component
@Slf4j
public class Job extends QuartzJobBean {

    @Value("${spring.timing.cron}")
    private String cron;

    @Autowired
    private Scheduler scheduler;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("执行定时任务");
        
        // TODO

        Trigger trigger = context.getTrigger();
        try {
            CronTrigger cronTrigger = (CronTrigger)
                    scheduler.getTrigger(trigger.getKey());
            String cronExpression = cronTrigger.getCronExpression();
            if (!cron.equals(cronExpression)) {
                //创建表达式调度器
                CronScheduleBuilder cronSchedule =
                        CronScheduleBuilder.cronSchedule(cron);
                //重构
                cronTrigger = cronTrigger.getTriggerBuilder()
                        .withIdentity(trigger.getKey())
                        .withSchedule(cronSchedule)
                        .build();
                scheduler.rescheduleJob(trigger.getKey(), cronTrigger);
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}

```