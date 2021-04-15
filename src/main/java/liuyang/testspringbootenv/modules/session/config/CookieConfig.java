package liuyang.testspringbootenv.modules.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 解决子域session共享问题
 * @author liuyang
 * @scine 2021/4/13
 *
 * 文档：
 * https://docs.spring.io/spring-session/docs/2.4.2/reference/html5/#api-cookieserializer
 *
 * 视频：4:10
 * https://www.bilibili.com/video/BV1np4y1C7Yf?p=228
 */
@Configuration
public class CookieConfig {

    // @Bean // 这个单个应用暂时不需要
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setDomainName("gulimall.com");// 明确指定是父域
        defaultCookieSerializer.setCookieName("GULISESSION");// 明确指定Cookie名称
        return defaultCookieSerializer;
    }
}
