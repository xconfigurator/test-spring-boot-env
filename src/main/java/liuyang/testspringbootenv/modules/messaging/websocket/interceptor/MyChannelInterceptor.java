package liuyang.testspringbootenv.modules.messaging.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * 应用场景：用户上线下线。用于获取消息的一些元数据
 *
 * 注册：在WebSocketMessageBrokerConfigurer的
 *
 * 视频：
 * https://www.bilibili.com/video/BV1sW411r7Tz?p=17&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author xconf
 * @since 2022/11/8
 */
//public class MyChannelInterceptor extends ChannelInterceptorAdapter {
@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.debug("preSend");
        // TODO
        //return null;
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.debug("postSend");
        // TODO
        // 在这个里面做一些处理
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
        if (stompHeaderAccessor.getCommand() == null) return; // 避免非stomp消息类型。e.g. 心跳检测的可以不处理。
        final String sessionId = stompHeaderAccessor.getSessionAttributes().get("sessionId").toString();
        log.debug("sessionId = {}", sessionId);

        switch (stompHeaderAccessor.getCommand()) {
            case CONNECT:
                doConnect(sessionId);
                break;
            case DISCONNECT:
                doDisconnect(sessionId);
                break;
            case SUBSCRIBE:
                doSubscribe();
                break;
            case UNSUBSCRIBE:
                doUnsubscribe();
                break;
            default:
                doDefault();
                break;
        }
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.debug("afterSendCompletion");
        // TODO
    }

    private void doConnect(String sessionId) {
        log.debug("doConnect sessionId = {}", sessionId);
    }

    private void doDisconnect(String sessionId) {
        log.debug("doDisconnect sessionId = {}", sessionId);
        // TODO 用户下线：在这里将用户列表中的用户移除即可。
    }

    private void doSubscribe() {
        log.debug("doSubscribe");
    }

    private void doUnsubscribe() {
        log.debug("doUnsubscribe");
    }

    private void doDefault() {
        log.debug("doDefault");
    }
}
