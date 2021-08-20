package liuyang.testspringbootenv.modules.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author liuyang
 * @scine 2021/7/29
 */
public class LiuyangEvent extends ApplicationEvent {
    // 需要提供一个构造方法 （根据IntelliJ提示即可）
    public LiuyangEvent(Object source) {
        super(source);
    }

    /*
    public void info() {
        System.out.println("监听到事件：" + this.getClass());
    }
     */
}
