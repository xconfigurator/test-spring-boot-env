package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
        return R.ok("Spring Boot 功能测试脚手架。IndexController");
    }

    // 如果想让默认进来就跳转到登录页
    /*
    @RequestMapping
    public ModelAndView index(ModelAndView mav) {
        log.info("自定义首页跳转 index redirect via public ModelAndView index(ModelAndView mav) ");
        mav.setViewName("redirect:/login");// spring security 默认提供的登录页
        //mav.setViewName("redirect:/sys/login/page");// 自定义登录页
        return mav;
    }*/
}
