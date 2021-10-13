package liuyang.testspringbootenv.modules.scheduler.springscheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class ScheduleService {

    /**
     * second, minute, hour, day of month, month, day of week
     *
     * 实测：Quartz and Spring Scheduler可以共存
     */
    @Scheduled(cron = "0/2 * * * * MON-SAT")//
    public void hello() {
        log.info("Spring Scheduler:: hello, Schedule! ");
    }

}
