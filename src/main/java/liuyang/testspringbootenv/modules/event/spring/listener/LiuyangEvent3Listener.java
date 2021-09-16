package liuyang.testspringbootenv.modules.event.spring.listener;

import liuyang.testspringbootenv.modules.event.spring.event.LiuyangEvent3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyang
 * @scine 2021/7/29
 * @update 2021/9/16
 */
@Component
@Slf4j
public class LiuyangEvent3Listener implements ApplicationListener<LiuyangEvent3> {
    @Override
    public void onApplicationEvent(LiuyangEvent3 event) {
        log.info("**********************hei! ");
        log.info("event = {}", event);
    }
}
