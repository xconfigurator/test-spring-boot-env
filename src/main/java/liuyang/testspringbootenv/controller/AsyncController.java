package liuyang.testspringbootenv.controller;

import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/3/30
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/sync")
    public R doSomething() throws Exception {
        // 同步方法
        String msg = asyncService.doSomething();
        return R.ok(msg);
    }

    @GetMapping("/async")
    public R doSomethingAsync() throws Exception {
        // 异步方法
        asyncService.doSomethingAsync();
        return R.ok("Async Service Returned! 方法返回，但任务线程仍在后台运行。观察后台日志来看任务完成情况。");
    }
}
