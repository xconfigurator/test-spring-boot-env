package liuyang.testspringbootenv.modules.messaging.websocket.dto;

import lombok.Data;

/**
 * @author xconf
 * @since 2022/11/8
 */
@Data
public class JVMInfo {
    private Integer availableProcessors;
    private Long maxMemory;
    private Long freeMemory;
}
