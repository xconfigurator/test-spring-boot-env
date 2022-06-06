package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.Id;
import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.scheduling.quartz.util.QuartzUtils;
import liuyang.testspringbootenv.modules.scheduling.spring.runnable.Hello01Runnable;
import liuyang.testspringbootenv.modules.scheduling.spring.util.TaskSchedulerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

/**
 * 测试
 * 1. TaskSchedulerUtils
 * 2. QuartzUtils 参见 test-quartz
 *
 * @author liuyang(wx)
 * @since 2022/6/6
 */
@RestController
@RequestMapping("scheduler")
@Slf4j
@ConditionalOnBean({TaskSchedulerUtils.class, QuartzUtils.class})// 我的意思是只有这俩工具类加载了，才暴露这个控制器。
public class ForTestSchedulerController {

    @Autowired
    public TaskSchedulerUtils taskSchedulerUtils;

    @GetMapping("add")
    public R addPeriodicDelayTaskSchedulerUtils(@RequestParam("delay") long delay) {
        log.info("delay = {}", delay);
        String taskId = Id.nextTaskId();
        taskSchedulerUtils.addPeriodic(taskId, delay, new Hello01Runnable());
        return R.ok("任务添加成功").put("taskId", taskId);
    }

    @GetMapping("stop")
    public R stop(@RequestParam("taskId") String taskId) {
        log.info("taskId = {}", taskId);
        taskSchedulerUtils.stop(taskId);
        return R.ok("任务停止成功").put("taskId", taskId);
    }

    @GetMapping("stopAll")
    public R stopAll() {
        taskSchedulerUtils.stopAll();
        return R.ok("全部任务停止成功");
    }

}
