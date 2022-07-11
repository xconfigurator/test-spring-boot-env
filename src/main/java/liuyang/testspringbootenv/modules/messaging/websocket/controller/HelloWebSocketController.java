package liuyang.testspringbootenv.modules.messaging.websocket.controller;

import liuyang.testspringbootenv.modules.messaging.websocket.dto.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author liuyang(wx)
 * @since 2022/7/11
 */
@Controller
public class HelloWebSocketController {

    @MessageMapping("/hello")// stompClient.send("/app/hello", {}, ...) 这个/app在 MessageBrokerRegistry .setApplicationDestinationPrefixes("/app"); 处设置。
    @SendTo("/topic/greetings")// stompClient.subscribe('/topic/greetings',...);
    public Message greeting(Message message) throws Exception {
        return message;
    }
}
