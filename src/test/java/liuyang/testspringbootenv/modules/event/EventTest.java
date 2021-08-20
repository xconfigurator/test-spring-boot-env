package liuyang.testspringbootenv.modules.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@SpringBootTest
public class EventTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    void testEvent() {
        // 实测：注册了哪个事件的监听器就会有哪个事件的响应。抛出一个没有监听器的事件，不会产生异常。
        ctx.publishEvent(new LiuyangEvent(new LiuyangEventDTO("event data for event1 01")));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEventDTO("event data for event2 01")));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEventDTO("event data for event2 02")));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEventDTO("event data for event2 03")));
        ctx.publishEvent(new LiuyangEvent(new LiuyangEventDTO("event data for event1 02")));
        ctx.publishEvent(new LiuyangEvent(new LiuyangEventDTO("event data for evnet1 03")));
        ctx.publishEvent(new LiuyangEvent(new LiuyangEventDTO("event data for evnet1 04")));
    }
}
