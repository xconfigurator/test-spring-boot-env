package liuyang.testspringbootenv.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {

    /**
     * second, minute, hour, day of month, month, day of week
     */
    @Scheduled(cron = "0 * * * * MON-SAT")
    public void hello() {
        // System.out.println("hello, Quartz");
        log.info("hello, Quartz");
    }

}
