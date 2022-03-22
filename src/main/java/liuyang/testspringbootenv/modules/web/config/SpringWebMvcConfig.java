package liuyang.testspringbootenv.modules.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 注册Spring MVC组件
 * @author liuyang
 * @scine 2021/4/19
 *
 * 作用相当于springmvc.xml
 */
@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {
    // Spring Boot 2.0 环境中 Thymeleaf貌似并不需要额外的配置
    // Spring Boot 1.0 中需要一些配置，详细参见ElasticSearch搜房网视频

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 20220124 add 是否有必要？ 实测不加也能运行。是否有加速效果未知。
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/")
                .resourceChain(false);
        registry.setOrder(1);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/").setViewName("/hello/hello"); // TODO ControllerExceptionHandler竟然没有控制住，为啥？？ 异常貌似是Thymeleaf抛出来的。
        // 这里可以添加一些直接页面跳转的，或者是更改名称的页面路径
        registry.addViewController("/foo").setViewName("redirect:/hello/hello");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO 在这里装配Interceptor
    }

    // 跨域请求处理
    // 2022/3/22貌似没有生效，暂时采用在对应控制器上增加@CrossOrigin注解方式，问题解决。
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }*/
}
