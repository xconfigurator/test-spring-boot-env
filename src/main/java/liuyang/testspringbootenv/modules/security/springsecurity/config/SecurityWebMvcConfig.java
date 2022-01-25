package liuyang.testspringbootenv.modules.security.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuyang(wx)
 * @since 2022/1/25
 */
@Configuration
public class SecurityWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 20220125 实测，这个mvc配置和web下的mvc配置可以共存。
        registry.addViewController("/login").setViewName("login");// 自定义登录页面入口
    }
}
