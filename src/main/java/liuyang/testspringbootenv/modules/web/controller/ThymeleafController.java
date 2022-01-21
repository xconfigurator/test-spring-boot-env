package liuyang.testspringbootenv.modules.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试Thymeleaf
 *
 * @author liuyang
 * @scine 2021/7/2
 */
@Controller
@RequestMapping("/th")
public class ThymeleafController {

    @GetMapping
    public String hello(Model model) {
        // 注：IntelliJ IDEA 启动maven工程的方式 clean package spring-boot:run -Dmaven.test.skip=true
        model.addAttribute("info", "test Spring Boot DevTools");
        model.addAttribute("username", "liuyang");
        model.addAttribute("testAttrHref", "http://www.baidu.com");
        return "index";
    }



}
