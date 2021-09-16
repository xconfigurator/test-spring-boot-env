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

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@SpringBootTest
public class SpringEventTest {

    @Autowired
    private ApplicationContext ctx;

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
        ctx.publishEvent(new LiuyangEvent3(new LiuyangEvent3DTO("hello, world")));
    }
}
