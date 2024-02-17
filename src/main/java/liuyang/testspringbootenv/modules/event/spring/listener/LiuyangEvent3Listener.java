package liuyang.testspringbootenv.modules.event.spring.listener;

import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author liuyang
 * @scine 2021/7/29
 * @update 2021/9/16
 */
@Component// 貌似这个不是必须的
@Slf4j
public class LiuyangEvent3Listener implements ApplicationListener<LiuyangEvent3> {

    @Async // liuyang:结论还是要加上这个的，以免影响主线程的执行速度！
    @Override
    public void onApplicationEvent(LiuyangEvent3 event) {
        log.info("**********************hei! ");
        log.info("event = {}", event);
        // 没加@Async
        /*
        2021-09-16 16:17:55.852 [main] INFO  l.t.m.event.spring.listener.LiuyangEvent3Listener - **********************hei!
        2021-09-16 16:17:55.852 [main] INFO  l.t.m.event.spring.listener.LiuyangEvent3Listener - event = liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent3[source=LiuyangEvent3DTO(foo=hello, world)]
         */
        // 加了@Async
        /*
        2021-09-16 16:27:04.736 [task-1] INFO  l.t.m.event.spring.listener.LiuyangEvent3Listener - **********************hei!
        2021-09-16 16:27:04.736 [task-1] INFO  l.t.m.event.spring.listener.LiuyangEvent3Listener - event = liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent3[source=LiuyangEvent3DTO(foo=hello, world)]
         */
    }
}
