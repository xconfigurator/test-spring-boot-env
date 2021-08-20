package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/3/31
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping
    public R hello(){
        return R.ok("URL:/hello");
    }

    @GetMapping("/hello")
    public R hellohello() {
        return R.ok("hello Spring Boot Env URL:hell/hello");
    }

    @GetMapping("/exception")
    public void excep() {
        throw new RuntimeException("测试异常处理");
    }


    @PostMapping
    public R testPost(String cityId){
        log.info(cityId);
        return R.ok().put("cityId", cityId);
    }
}
