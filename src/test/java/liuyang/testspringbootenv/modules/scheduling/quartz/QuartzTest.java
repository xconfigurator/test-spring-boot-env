package liuyang.testspringbootenv.modules.scheduling.quartz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liuyang
 * @scine 2021/9/16
 */
@SpringBootTest
@Slf4j
public class QuartzTest {

    @Autowired
    Scheduler scheduler;

    @Test
    void testSchedulerStatus() throws SchedulerException {
        log.info("scheduler is started = {}", scheduler.isStarted());
    }

    @Test
    void testSchedulerStart() throws SchedulerException {
        scheduler.start();
        scheduler.start();
        scheduler.start();
    }
}
