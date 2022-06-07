package liuyang.testspringbootenv.modules.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author liuyang(wx)
 * @since 2022/6/7
 */
@ConditionalOnProperty(prefix = "enable", name = "modules.runner.foo", havingValue = "true")
@Component// 生效需要添加这个
@Slf4j
public class FooRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner foo...");
    }
}
