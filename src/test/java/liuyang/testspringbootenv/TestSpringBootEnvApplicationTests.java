package liuyang.testspringbootenv;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TestSpringBootEnvApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void hello() {
        log.info("hello, from IntelliJ IDEA Ultimate");
    }
}
