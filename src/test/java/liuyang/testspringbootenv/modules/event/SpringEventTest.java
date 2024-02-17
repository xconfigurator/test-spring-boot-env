package liuyang.testspringbootenv.modules.event;

import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent2DTO;
import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent3DTO;
import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent1;
import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent2;
import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent1DTO;
import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@SpringBootTest
public class SpringEventTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private ApplicationEventPublisher aep; // 20240215 调用这个发

    @Test
    void testEvent202402151304() {
        // 实测：注册了哪个事件的监听器就会有哪个事件的响应。抛出一个没有监听器的事件，不会产生异常。
        // 在EventListener中注册的监听器
        aep.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for event1 01")));
        aep.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        aep.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        aep.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        aep.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for event1 02")));
        aep.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for evnet1 03")));
        aep.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for evnet1 04")));
        // 使用继承ApplicationListener方式
        aep.publishEvent(new LiuyangEvent3(new LiuyangEvent3DTO("hello, world")));// 这个Listener演示了异步多线程处理。
    }

    @Test
    void testEvent() {
        // 实测：注册了哪个事件的监听器就会有哪个事件的响应。抛出一个没有监听器的事件，不会产生异常。
        // 在EventListener中注册的监听器
        ctx.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for event1 01")));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        ctx.publishEvent(new LiuyangEvent2(new LiuyangEvent2DTO(1, 2)));
        ctx.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for event1 02")));
        ctx.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for evnet1 03")));
        ctx.publishEvent(new LiuyangEvent1(new LiuyangEvent1DTO("############event data for evnet1 04")));
        // 使用继承ApplicationListener方式
        ctx.publishEvent(new LiuyangEvent3(new LiuyangEvent3DTO("hello, world")));// 这个Listener演示了异步多线程处理。
    }
}
