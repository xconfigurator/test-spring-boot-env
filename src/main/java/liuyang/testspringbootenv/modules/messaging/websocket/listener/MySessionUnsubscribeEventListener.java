package liuyang.testspringbootenv.modules.messaging.websocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

/**
 * @author xconf
 * @since 2022/11/8
 */
@Component
@Slf4j
public class MySessionUnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent> {
    @Override
    public void onApplicationEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.debug("WebSocket Session监听器 事件类型：{}", stompHeaderAccessor.getCommand().getMessageType());// UNSUBSCRIBE
    }
}
