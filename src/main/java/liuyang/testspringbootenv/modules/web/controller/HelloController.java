package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/3/31
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/hello")
    public R hello() {
        return R.ok("hello Spring Boot Env");
    }

    @GetMapping("/exception")
    public void excep() {
        throw new RuntimeException("测试异常处理");
    }
}
