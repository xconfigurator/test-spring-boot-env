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

    /*
    处理如下情况的session共享：
    1. 同域名下不同项目
    2. 同根域名不同二级子域名
    注：同域名下同项目（集群）的情况，只需要加入starter即可生效。
     */
    // @Bean // 这个单个应用暂时不需要
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();

        // 情况1： 实现同域名不同项目session共享
        defaultCookieSerializer.setCookiePath("/");

        // 情况2： 实现同根域名不同二级子域名项目session共享 (Nginx虚拟主机)
        // http://bj.58.com http://hb.58.com
        // 上面的情况1的配置不影响下面的配置。
        defaultCookieSerializer.setDomainName("gulimall.com");// 明确指定是父域（根域名）
        defaultCookieSerializer.setCookieName("GULISESSION");// 明确指定Cookie名称

        return defaultCookieSerializer;
    }
}
