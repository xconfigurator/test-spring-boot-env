package liuyang.testspringbootenv.modules.messaging.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 应用场景：通过这个组件的方法有机会访问request，response。
 *
 * 注册：在配置endpoint的时候
 *
 * 视频：
 * https://www.bilibili.com/video/BV1sW411r7Tz/?p=14&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 *
 * @author xconf
 * @since 2022/11/8
 */
@Slf4j
public class MyHandshakeInteceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.debug("beforeHandshake");

        // 从request中拿出放到attributes中，后面的处理就都可以从里面拿值。
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
            String sessionId = session.getId();
            attributes.put("sessionId", sessionId);
        }

        //return false;
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.debug("afterHandshake");
    }
}
