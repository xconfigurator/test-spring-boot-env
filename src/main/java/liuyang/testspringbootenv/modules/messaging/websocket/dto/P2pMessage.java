package liuyang.testspringbootenv.modules.messaging.websocket.dto;

import lombok.Data;

/**
 * @author liuyang(wx)
 * @since 2022/7/11
 */
@Data
public class P2pMessage {
    private String to;
    private String from;
    private String content;
}
