package liuyang.testspringbootenv.modules.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
@Deprecated
//@Component
public class LiuyangEventListener implements ApplicationListener<LiuyangEvent> {
    @Override
    public void onApplicationEvent(LiuyangEvent event) {
        //event.info();
        System.out.println("监听到事件:" + event.getClass());
    }
}
