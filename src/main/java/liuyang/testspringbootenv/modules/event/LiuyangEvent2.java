package liuyang.testspringbootenv.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
public class LiuyangEvent2 extends ApplicationEvent {
    public LiuyangEvent2(Object source) {
        super(source);
    }
}
