package liuyang.testspringbootenv.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    /**
     * second, minute, hour, day of month, month, day of week
     */
    @Scheduled(cron = "0 * * * * MON-SAT")
    public void hello() {
        System.out.println("hello, Quartz");
    }

}
