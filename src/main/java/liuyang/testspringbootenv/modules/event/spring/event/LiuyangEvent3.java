package liuyang.testspringbootenv.modules.event.spring.event;

import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent3DTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author liuyang
 * @scine 2021/9/16
 */
public class LiuyangEvent3 extends ApplicationEvent {
    public LiuyangEvent3(LiuyangEvent3DTO dto) {
        super(dto);
    }
}
