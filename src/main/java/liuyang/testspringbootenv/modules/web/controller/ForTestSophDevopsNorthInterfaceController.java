package liuyang.testspringbootenv.modules.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang(wx)
 * @since 2022/5/24
 */
@RestController
//@Controller
@RequestMapping("inma_smart")
@Slf4j
public class ForTestSophDevopsNorthInterfaceController {
    // 为测试烽火北向接口使用的
    // 返回报文 3.2.2 HTTP 协议回复标准
    /*
    3.2.2 HTTP 协议回复标准
    1. HTTP 标准响应码：成功 200、5XX 服务器错误、4XX 权限不足。
    2. HTTP 请求成功（状态码 200）时统一返回 JSON 格式：{ "code": 200, "reason": "请求成功", "result": object}，备注 code：200 功。
    3. code：请求成功、reason：提示、result：接口返回具体格式。
    */

    @PostMapping(value = "alarm")
    public SophResult alarm(@RequestBody String json) {
        log.info("上传的json = {}", json);
        return new SophResult();
    }

    @PostMapping("performance")
    public String performance(@RequestBody String json) {
        log.info("上传的json = {}", json);
        return JSON.toJSONString(new SophResult());
    }

    @PostMapping("operation")
    public SophResult operation(@RequestBody String json) {
        log.info("上传的json = {}", json);
        return new SophResult();
    }

    @PostMapping("util")
    public SophResult util(@RequestBody String json) {
        log.info("上传的json = {}", json);
        return new SophResult();
    }

    @Data // 试一试不写get set。试试就逝世。
    public class SophResult {
        private Integer code = 200;
        private String reason = "请求成功";
        private Object result = null;
    }
}
