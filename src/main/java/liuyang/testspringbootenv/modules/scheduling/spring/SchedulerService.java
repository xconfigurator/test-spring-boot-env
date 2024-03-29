package liuyang.testspringbootenv.modules.scheduling.spring;

import liuyang.testspringbootenv.modules.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "enable", name="modules.scheduler.spring", havingValue = "true")
@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private AsyncService asyncService;

    /**
     * second, minute, hour, day of month, month, day of week
     *
     * 实测：Quartz and Spring Scheduler可以共存
     */
    //@Scheduled(cron = "0/2 * * * * MON-SAT")//
    public void hello() throws InterruptedException {
        log.info("Spring Scheduler:: hello, Schedule! ");
        asyncService.doSomethingAsync();
    }

}
