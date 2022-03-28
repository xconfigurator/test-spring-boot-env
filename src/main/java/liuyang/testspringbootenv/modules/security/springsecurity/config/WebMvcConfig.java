package liuyang.testspringbootenv.modules.security.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuyang(wx)
 * @since 2022/1/25
 * @update 2022/3/28 添加跨域处理
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 前后端不分离的登录入口
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 20220125 实测，这个mvc配置和web下的mvc配置可以共存。
        registry.addViewController("/login").setViewName("login");// 自定义登录页面入口
    }

    /**
     * 跨域处理（前后端分离项目中必须）
     * 参考视频：https://www.bilibili.com/video/BV1mm4y1X7Hc?p=32
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许Cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
