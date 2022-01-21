package liuyang.testspringbootenv.modules.web.controller;

import liuyang.testspringbootenv.common.utils.IdUtils;
import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 试验性地提供数据
 * @author liuyang(wx)
 * @since 2022/1/20
 */
@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    @GetMapping
    public R data() {
        return R.ok("本控制器返回一些测试数据");
    }

    // ////////////////////////////////////////////////////////////////////
    // 请求和响应
    // 请求参数 参考文档：https://www.yuque.com/atguigu/springboot/vgzmgh#xNDeL
    // @PathVa


    // ////////////////////////////////////////////////////////////////////
    // 202111261016 为测试RestTemplate定制的方法 begin
    @GetMapping("/dto")
    public UserDTO testRestTemplateDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(IdUtils.nextTaskId());
        userDTO.setUsername("liuyang");
        userDTO.setInfo("测试RestTemplate的getForObject()");
        userDTO.setD(9.0);
        userDTO.setBd(new BigDecimal("40.000000"));
        return userDTO;
    }

    @PostMapping("/dtopost1")
    //@GetMapping("/dtopost1")
    public UserDTO testRestTemplateDtoPost1(String id, String username, String info, Double d, BigDecimal bd) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setUsername(username);
        userDTO.setInfo(info);
        userDTO.setD(d);
        userDTO.setBd(bd);
        log.info("userDTO = {}", userDTO);
        return userDTO;
    }

    @PostMapping("/dtopost2")
    public UserDTO testRestTemplateDtoPost2(@RequestBody UserDTO userDTO) {
        log.info("userDTO = {}", userDTO);
        return userDTO;
    }
    // 202111261016 为测试RestTemplate定制的方法 end

}
