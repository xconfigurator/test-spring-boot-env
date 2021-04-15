package liuyang.testspringbootenv.modules.job.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleService {

    /**
     * second, minute, hour, day of month, month, day of week
     */
    @Scheduled(cron = "0 * * * * MON-SAT")
    public void hello() {
        log.info("hello, Schedule! ");
    }

}
