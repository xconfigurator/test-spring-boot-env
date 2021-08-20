package liuyang.testspringbootenv.modules.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@Configuration
public class EventListenerConfig {

    @EventListener
    public void liuyangEventListener(LiuyangEvent event) {
        System.out.println("监听到事件:" + event.getClass());
        LiuyangEventDTO data = (LiuyangEventDTO)event.getSource();
        System.out.println(data);
    }

    @EventListener
    public void liuyangEvent2Listener(LiuyangEvent2 event) {
        System.out.println("监听到事件:" + event.getClass());
        LiuyangEventDTO data = (LiuyangEventDTO)event.getSource();
        System.out.println(data);
    }
}
