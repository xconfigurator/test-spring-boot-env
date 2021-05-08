package liuyang.testspringbootenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
// @EnableScheduling
@SpringBootApplication
public class TestSpringBootEnvApplication {

    public static void main(String[] args) {
        // SpringApplication.run(TestSpringBootEnvApplication.class, args);

        // 看看自动配置
        ConfigurableApplicationContext run = SpringApplication.run(TestSpringBootEnvApplication.class, args);
        String[] beanDefinitionNames = run.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            log.info(name);
        }
    }

}
