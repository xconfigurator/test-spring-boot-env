package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/10/13
 */
@RestController
@RequestMapping
@Slf4j
public class IndexController {

    @RequestMapping
    public R index() {
        return R.ok("Spring Boot 功能测试脚手架。");
    }
}
