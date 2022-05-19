package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.Id;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/2018")
    public String v2018(Model model) {
        model.addAttribute("info", "hello, thymeleaf v2018");
        model.addAttribute("infoLabel", "<h1>测试Thymeleaf</h1>");
        model.addAttribute("users", getUserList());
        return "index2018";
    }

    private List<UserDTO> getUserList() {
        List<UserDTO> list = new ArrayList<>();
        UserDTO user1 = new UserDTO();
        user1.setId(Id.nextTaskId());
        list.add(user1);
        UserDTO user2 = new UserDTO();
        user2.setId(Id.nextTaskId());
        list.add(user2);
        return list;
    }
}
