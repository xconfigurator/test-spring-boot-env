package liuyang.testspringbootenv.modules.event.spring.event;

import liuyang.testspringbootenv.modules.event.spring.dto.LiuyangEvent1DTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
public class LiuyangEvent1 extends ApplicationEvent {
    // 需要提供一个构造方法 （根据IntelliJ提示即可）
    public LiuyangEvent1(LiuyangEvent1DTO dto) {
        super(dto);
    }

    /*
    public void info() {
        System.out.println("监听到事件：" + this.getClass());
    }
     */

}
