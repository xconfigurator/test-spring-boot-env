package liuyang.testspringbootenv.modules.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuyang
 * @scine 2021/4/19
 *
 * 作用相当于springmvc.xml
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/").setViewName("/hello/hello"); // TODO ControllerExceptionHandler竟然没有控制住，为啥？？ 异常貌似是Thymeleaf抛出来的。
        registry.addViewController("/").setViewName("redirect:/hello/hello");
    }

    // Spring Boot 2.0 环境中 Thymeleaf貌似并不需要额外的配置
    // Spring Boot 1.0 中需要一些配置，详细参见ElasticSearch搜房网视频
}
