package liuyang.testspringbootenv.modules.web.config;

import liuyang.testspringbootenv.modules.web.converters.MyYamlHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 注册Spring MVC组件
 * @author liuyang
 * @scine 2021/4/19
 *        2022/6/20 增加跨域请求
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
        registry.addViewController("/chat_room").setViewName("chat_room");// 20220711 实测，这个跳转还必须得加上，否则404
        registry.addViewController("/chat_p2p").setViewName("chat_p2p");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器介绍
        // HandlerInterceptor
        // https://www.bilibili.com/video/BV1Km4y1k7bn/?p=146&vd_source=8bd7b24b38e3e12c558d839b352b32f4

        // 多个拦截器的顺序
        // order越小越先执行 .order(1)
        // https://www.bilibili.com/video/BV1Km4y1k7bn?p=148&vd_source=8bd7b24b38e3e12c558d839b352b32f4
    }

    // 跨域请求处理
    // 2022/3/22貌似没有生效，暂时采用在对应控制器上增加@CrossOrigin注解方式，问题解决。
    // https://blog.csdn.net/chang100111/article/details/116697006
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }*/

    /*
    private static final int MAX_AGE = 3600;

    // 协议、域名、端口号。有任何一个不同，都会被浏览器视为跨域。
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 这样配置相当于在指定URL(addMapping)的响应头（response.setHeader()）增加注释中的字段
        registry.addMapping("/**")
                .allowCredentials(true)      // Access-Control-Allow-Credentials // 允许携带Cookie
                //.allowedOrigins("*")         // Access-Control-Allow-Origin // 500!
                .allowedOriginPatterns("*")
                .allowedMethods("*")         // Access-Control-Allow-Methods
                //.allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")         // Access-Control-Allow-Headers
                .maxAge(MAX_AGE);            // Access-Control-Max-Age
    }
     */

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(new MyYamlHttpMessageConverter());// 20231230 实测，这样不会影响已经有的转换器工做。
    }
}
