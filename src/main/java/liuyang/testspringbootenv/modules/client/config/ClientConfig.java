package liuyang.testspringbootenv.modules.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * docs:
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-resttemplate
 * https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client
 *
 * RestTemplate: The original Spring REST client with a synchronous, template method API.
 * WebClient: a non-blocking, reactive alternative that supports both synchronous and asynchronous as well as streaming scenarios.
 *
 * As of 5.0 the RestTemplate is in maintenance mode, with only minor requests for changes and bugs to be accepted going forward.
 * Please, consider using the WebClient which offers a more modern API and supports sync, async, and streaming scenarios.
 *
 * @author liuyang(wx)
 * @since 2021/11/26
 */
@Component
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 关于初始化: 找实现了ClientHttpRequestFactory接口的类，注册进RestTemplate即可。
        // 1. 底层使用java.net.HttpURLConnection
        // restTemplate = new RestTemplate();
        // 2. 底层使用ApacheHttpComponents
        // restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        // 3. 底层使用Netty
        // 4. 底层使用OkHttp
        return new RestTemplate();
    }
}
