package liuyang.testspringbootenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableCaching          // Spring cache abstraction (Initializr里目前被分类在I/O下)
@EnableRedisHttpSession // Spring Session Data Redis (Initializr分类在Web下)
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class TestSpringBootEnvApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootEnvApplication.class, args);
    }

}
