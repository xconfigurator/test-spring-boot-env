package liuyang.testspringbootenv.modules.event.spring.listener;

import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent2DTO;
import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent1;
import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent2;
import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent1DTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@Configuration
public class EventListenerConfig {

    @EventListener
    public void liuyangEventListener(LiuyangEvent1 event) {
        System.out.println("监听到事件:" + event.getClass());
        LiuyangEvent1DTO data = (LiuyangEvent1DTO)event.getSource();
        System.out.println(data);
    }

    @EventListener
    public void liuyangEvent2Listener(LiuyangEvent2 event) {
        System.out.println("监听到事件:" + event.getClass());
        LiuyangEvent2DTO data = (LiuyangEvent2DTO)event.getSource();
        System.out.println(data);
    }
}
