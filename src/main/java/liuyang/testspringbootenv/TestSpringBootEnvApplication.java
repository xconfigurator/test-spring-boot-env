package liuyang.testspringbootenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.net.InetAddress;
import java.net.UnknownHostException;

// 这些@EnableXX可以分别写在不同的@Configuration中 2021/7/1
//@EnableCaching          // Spring cache abstraction (Initializr里目前被分类在I/O下)(20220330 放在了cache包配置类上)
//@EnableRedisHttpSession // Spring Session Data Redis (Initializr分类在Web下)(20220330 放在了session/redis的配置类上)
//@EnableScheduling       // 定时任务(20220330 放在了scheduler/springscheduled的配置类上)
//@EnableAsync            // 异步方法(20220330 放在了async包配置类上)
@SpringBootApplication
@Slf4j
public class TestSpringBootEnvApplication {

    public static void main(String[] args) throws UnknownHostException {
        //SpringApplication.run(TestSpringBootEnvApplication.class, args);

        // liuyang 20210528 begin
        ConfigurableApplicationContext applicatonContext = SpringApplication.run(TestSpringBootEnvApplication.class, args);
        ConfigurableEnvironment environment = applicatonContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");// 实际提供服务的端口，如果开启SSL, 一般情况下就是8443
        String path = environment.getProperty("spring.application.name");
        //String httpPort = environment.getProperty("http.port");// 项目对外提供服务的端口
        String httpPort = port;
        log.info("#################################################################");
        log.info("启动完毕。");
        if (null == path) {
            log.info("本地：      \thttp://localhost:" + httpPort + "/");
            log.info("外部：      \thttp://" + ip + ":" + httpPort + "/");
            log.info("swagger-ui:\thttp://" + ip + ":" + httpPort + "/doc.html");
            log.info("druid:     \thttp://localhost:" + httpPort + "/druid/");// ip的方式默认被禁止
        } else {
            log.info("本地：      \thttp://localhost:" + httpPort + "/" + path + "/");
            log.info("外部：      \thttp://" + ip + ":" + httpPort + "/" + path + "/");
            log.info("swagger-ui:\thttp://" + ip + ":" + httpPort + "/" + path + "/doc.html");
            log.info("druid:     \thttp://localhost:" + httpPort + "/" + path + "/druid/");// ip的方式默认被禁止
        }
        // liuyang 20210528 end
        System.out.println("xxx");
    }

}
