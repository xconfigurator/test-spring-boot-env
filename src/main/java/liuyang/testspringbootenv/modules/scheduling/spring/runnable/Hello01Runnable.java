package liuyang.testspringbootenv.modules.scheduling.spring.runnable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuyang(wx)
 * @since 2022/6/6
 */
@Slf4j
public class Hello01Runnable implements Runnable{

    @Override
    public void run() {
        log.info("测试一个实现了Runnable的类被Spring提供的TaskScheduler调度起来！");
    }
}
