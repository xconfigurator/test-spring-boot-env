package liuyang.testspringbootenv;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

@SpringBootTest
@Slf4j
class TestSpringBootEnvApplicationTests {

    @Autowired
    ConfigurableApplicationContext ctx;

    @Autowired
    ConfigurableEnvironment environment;

    @Test
    void contextLoads() {
    }

    @Test
    void hello() {
        log.info("hello, from IntelliJ IDEA Ultimate");
    }

    @Test
    void envrionment() {
        // ConfigurableEnvironment environment = run.getEnvironment();
        log.info("##############################################");
        log.info("systemEnvrionment");
        Map<String, Object> systemEnvironment = environment.getSystemEnvironment();
        //System.out.println(systemEnvironment);
        systemEnvironment.entrySet().stream().forEach(System.out::println);

        log.info("##############################################");
        log.info("systemProperties");
        Map<String, Object> systemProperties = environment.getSystemProperties();
        //System.out.println(systemProperties);
        systemEnvironment.entrySet().stream().forEach(System.out::println);
    }
}
