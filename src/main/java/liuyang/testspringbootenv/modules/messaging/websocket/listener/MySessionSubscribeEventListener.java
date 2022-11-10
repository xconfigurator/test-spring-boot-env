package liuyang.testspringbootenv.modules.messaging.websocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * @author xconf
 * @since 2022/11/8
 */
@Component// 直接这样，配合实现的接口，就可以作为事件的监听器.
@Slf4j
public class MySessionSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.debug("WebSocket Session监听器 事件类型：{}", stompHeaderAccessor.getCommand().getMessageType() );// SUBSCRIBE
        log.debug("sessionId = {}", stompHeaderAccessor.getSessionAttributes().get("sessionId"));// 从MyHandshakeInteceptor中放入SessionAttributes中的。
    }
}
