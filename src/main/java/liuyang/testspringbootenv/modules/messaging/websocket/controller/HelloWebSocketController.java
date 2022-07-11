package liuyang.testspringbootenv.modules.messaging.websocket.controller;

import liuyang.testspringbootenv.modules.messaging.websocket.dto.Message;
import liuyang.testspringbootenv.modules.messaging.websocket.dto.P2pMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author liuyang(wx)
 * @since 2022/7/11
 */
@Controller
@Slf4j
public class HelloWebSocketController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    // //////////////////////////////////////////////////////
    // 群发
    // 转发方式1 @SendTo 202207111416 ok
    //@MessageMapping("/hello")// stompClient.send("/app/hello", {}, ...) 这个/app在 MessageBrokerRegistry .setApplicationDestinationPrefixes("/app"); 处设置。
    //@SendTo("/topic/greetings")// stompClient.subscribe('/topic/greetings',...);
    public Message greeting(Message message) throws Exception {
        return message;
    }

    // 转发方式2 SimpMessagingTemplate 202207111420 ok
    @MessageMapping("/hello")
    public void greeting2(Message message) throws Exception {
        simpMessagingTemplate.convertAndSend("/topic/greetings", message);// stompClient.subscribe('/topic/greetings',...);
    }

    // //////////////////////////////////////////////////////
    // 点对点
    @MessageMapping("/chat")// stompClient.send("/app/chat", {}, ...)
    public void chat(Principal principal, P2pMessage p2pMessage) {
        log.debug("## principal.getName() = {}", principal.getName());
        String from = principal.getName();
        p2pMessage.setFrom(from);
        simpMessagingTemplate.convertAndSendToUser(p2pMessage.getTo(), "/queue/chat", p2pMessage);// stompClient.subscribe('/user/queue/chat', ...);
    }
}
