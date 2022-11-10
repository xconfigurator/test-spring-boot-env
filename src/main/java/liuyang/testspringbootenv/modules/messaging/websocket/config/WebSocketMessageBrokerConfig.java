package liuyang.testspringbootenv.modules.messaging.websocket.config;

import liuyang.testspringbootenv.modules.messaging.websocket.interceptor.MyChannelInterceptor;
import liuyang.testspringbootenv.modules.messaging.websocket.interceptor.MyHandshakeInteceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 视频教程：
 * https://www.bilibili.com/video/BV1sW411r7Tz/?p=6&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4 （这个视频讲解了一个根Spring官方的Guides类似的例子）
 *
 * Guides:
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 *
 * @author liuyang(wx)
 * @since 2022/7/11
 *        2022/11/8 https://www.bilibili.com/video/BV1sW411r7Tz/?p=6&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4 12:53
 */
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling// 测试一下其他地方开启了这里再写会不会有问题。 本包中目前只有JVMInfoService会使用定时任务。 202211101508 实测不会有问题！
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    // 发布或者订阅消息的时候需要连接此端点（可类比手机基站）
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 前端：var socket = new SockJS("/chat");
        registry.addEndpoint("/gs-guide-websocket-endpoint")
                //.setAllowedOrigins("*")// 允许其他域进行连接
                .setAllowedOriginPatterns("*")//
                .addInterceptors(new MyHandshakeInteceptor())
                .withSockJS();// 开启SockJS支持

        // 20221110 关于跨域
        // 参考帖子：https://www.cnblogs.com/kanie-life/p/14285217.html
        /*
        2022-11-10 14:22:09.406 [http-nio-80-exec-9] ERROR l.t.common.exception.RestControllerExceptionAdvice - When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
java.lang.IllegalArgumentException: When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
         */

    }

    // 配置消息代理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 说明：
         * to enable a simple memory-based message broker to carry the greeting messages back to the client on destinations prefixed with /topic
         */
        //registry.enableSimpleBroker("/topic");
        registry.enableSimpleBroker("/topic", "/queue");

        /**
         * 说明：
         * designates the /app prefix for messages that are bound for methods annotated with @MessageMapping.
         * This prefix will be used to define all the message mappings.
         * For example, /app/hello is the endpoint that the GreetingController.greeting() method is mapped to handle.
         */
        /**
         * 附录：
         * @Controller
         * public class GreetingController {
         *
         *
         *   @MessageMapping("/hello")
         *   @SendTo("/topic/greetings")
         *   public Greeting greeting(HelloMessage message) throws Exception {
         *     Thread.sleep(1000); // simulated delay
         *     return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
         *   }
         *
         * }
         */
        //registry.setApplicationDestinationPrefixes("/app");
        registry.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 注：可以定制线程池
        //registration.taskExecutor().corePoolSize(10);

        // 定制拦截器
        registration.interceptors(new MyChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // TODO

    }
}
