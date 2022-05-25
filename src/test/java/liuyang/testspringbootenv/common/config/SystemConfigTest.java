package liuyang.testspringbootenv.common.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liuyang(wx)
 * @since 2022/5/25
 */
@SpringBootTest
@Slf4j
public class SystemConfigTest {

    @Autowired
    private SystemConfig systemConfig;

    @Test
    void testFoo() {
        log.info("foo = {}", systemConfig.getFoo());
    }
}
