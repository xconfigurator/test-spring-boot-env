package liuyang.testspringbootenv.modules.scheduler.springscheduled.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuyang(wx)
 * @since 2022/3/30
 */
//@ConditionalOnProperty(prefix = "enable", name="modules.scheduler.springscheduled", havingValue = "true")// 2022/6/6 实测，写到这里并不顶用。必须要标注到Service上。
@Configuration
@EnableScheduling
public class ScheduleConfig {
}
