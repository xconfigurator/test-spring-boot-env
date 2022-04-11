package liuyang.testspringbootenv.modules.web.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 配合test-client-httpclient4项目用
 * @author liuyang(wx)
 * @since 2022/4/11
 */
@RestController
@RequestMapping("test-client-httpclient4")
@Slf4j
public class ForTestClientHttpClient4Controller {

    @GetMapping
    public R foo() {
        return R.ok("hello for test-client-httpclient4");
    }

    @GetMapping("test1")
    public String test1(UserDTO userDTO, HttpServletRequest httpServletRequest) {
        log.info("userDTO = {}", JSON.toJSONString(userDTO));

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            StringBuilder sb = new StringBuilder();
            sb.append(headerName);
            sb.append(" : ");
            sb.append(httpServletRequest.getHeader(headerName));
            System.out.println(sb.toString());
        }

        return "test1";
    }
}
