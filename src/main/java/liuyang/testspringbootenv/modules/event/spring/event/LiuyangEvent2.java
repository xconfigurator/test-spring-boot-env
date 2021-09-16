package liuyang.testspringbootenv.modules.event.spring.event;

import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent2DTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
public class LiuyangEvent2 extends ApplicationEvent {
    public LiuyangEvent2(LiuyangEvent2DTO dto) {
        super(dto);
    }
}
